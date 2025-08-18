package com.hospital.repo;

import com.hospital.model.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryDB {

    // ==== Users & simple metadata ====
    public static final Map<String, User> users = new ConcurrentHashMap<>();
    // Opsiyonel: görünen ad / isim
    public static final Map<String, String> userFullNameByUsername = new ConcurrentHashMap<>();
    // Hasta kullanıcı adı -> TC eşlemesi (User modelini değiştirmeden TC tutmak için)
    public static final Map<String, String> patientTcByUsername = new ConcurrentHashMap<>();

    // ==== Appointments ====
    public static final Map<Long, Appointment> appointments = new ConcurrentHashMap<>();
    public static final AtomicLong seqAppointment = new AtomicLong(1);

    // ==== OR routing ====
    public static final Map<Long, Room> rooms   = new ConcurrentHashMap<>();
    public static final Map<Long, Source> sources = new ConcurrentHashMap<>();
    public static final Map<Long, Route> routes = new ConcurrentHashMap<>(); // key: roomId

    // ==== Recordings ====
    public static final Map<Long, Recording> recordings = new ConcurrentHashMap<>();
    public static final AtomicLong seqRecording = new AtomicLong(1);

    // ==== Presets ====
    public static final Map<Long, Preset> presets = new ConcurrentHashMap<>();
    public static final AtomicLong seqPreset = new AtomicLong(1);

    // ==== Annotations ====
    public static final Map<Long, Annotation> annotations = new ConcurrentHashMap<>();
    public static final AtomicLong seqAnnotation = new AtomicLong(1);

    // ==== Audit ====
    public static final Map<Long, AuditEntry> audit = new ConcurrentHashMap<>();
    public static final AtomicLong seqAudit = new AtomicLong(1);

    // ==== Patient Records (Hasta Geçmişi) ====
    // TC -> kayıt listesi
    public static final Map<String, List<PatientRecord>> recordsByTc = new ConcurrentHashMap<>();
    public static final AtomicLong seqRecord = new AtomicLong(1);

    static {
        // ---- Users ----
        users.put("admin",   new User("admin",   "1234", "admin"));
        users.put("doktor1", new User("doktor1", "1234", "doctor"));
        users.put("doktor2", new User("doktor2", "1234", "doctor"));
        users.put("hasta1",  new User("hasta1",  "1234", "patient"));
        users.put("hasta2",  new User("hasta2",  "1234", "patient"));

        // Görünen adlar (opsiyonel)
        userFullNameByUsername.put("admin",   "Sistem Yöneticisi");
        userFullNameByUsername.put("doktor1", "Dr. Ahmet Yılmaz");
        userFullNameByUsername.put("doktor2", "Dr. Zeynep Kaya");
        userFullNameByUsername.put("hasta1",  "Ayşe Demir");
        userFullNameByUsername.put("hasta2",  "Mehmet Çetin");

        // Hasta -> TC eşlemesi
        patientTcByUsername.put("hasta1", "12345678901");
        patientTcByUsername.put("hasta2", "12345678902");

        // ---- Rooms ----
        rooms.put(1L, new Room(1L, "OR-1"));
        rooms.put(2L, new Room(2L, "OR-2"));

        // ---- Sources (demo) ----
        sources.put(1L, new Source(1L, "Endoscope-1 (HLS)",
                "https://test-streams.mux.dev/x36xhzz/x36xhzz.m3u8", true));
        sources.put(2L, new Source(2L, "CeilingCam (MP4)",
                "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4", true));
        sources.put(3L, new Source(3L, "RoomCam (MP4)",
                "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4", true));

        // Varsayılan route OR-1 -> Source-2
        routes.put(1L, new Route(1L, 2L, now()));
        addAudit("system", "ROUTE_SET", "OR-1 -> CeilingCam");

        // ---- Örnek Hasta Geçmişi (records) ----
        // hasta1 (TC: 12345678901) için 2 örnek kayıt

    }

    // ========= Utils =========
    public static String now() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public static void addAudit(String actor, String action, String details) {
        long id = seqAudit.getAndIncrement();
        audit.put(id, new AuditEntry(id, actor, action, details, now()));
    }

    // ========= Users =========
    public static List<User> listByRole(String role) {
        List<User> out = new ArrayList<>();
        for (User u : users.values())
            if (role.equalsIgnoreCase(u.getRole())) out.add(u);
        return out;
    }
    public static List<User> listAllUsers() { return new ArrayList<>(users.values()); }

    // ========= Appointments =========
    public static Appointment addAppointment(Appointment a) {
        long id = seqAppointment.getAndIncrement();
        a.setId(id);
        if (a.getStatus() == null || a.getStatus().isBlank()) {
            a.setStatus("scheduled");
        }
        appointments.put(id, a);
        addAudit(a.getPatientUsername(), "APPOINTMENT_CREATED",
                "doctor="+a.getDoctorUsername()+", patient="+a.getPatientUsername()+", at="+a.getDatetime());
        return a;
    }

    public static List<Appointment> allAppointments() {
        List<Appointment> out = new ArrayList<>(appointments.values());
        out.sort(Comparator.comparing(Appointment::getDatetime));
        return out;
    }

    public static List<Appointment> byDoctor(String doctorUsername) {
        List<Appointment> out = new ArrayList<>();
        for (Appointment a : appointments.values())
            if (doctorUsername.equalsIgnoreCase(a.getDoctorUsername())) out.add(a);
        out.sort(Comparator.comparing(Appointment::getDatetime));
        return out;
    }

    public static List<Appointment> byPatient(String patientUsername) {
        List<Appointment> out = new ArrayList<>();
        for (Appointment a : appointments.values())
            if (patientUsername.equalsIgnoreCase(a.getPatientUsername())) out.add(a);
        out.sort(Comparator.comparing(Appointment::getDatetime));
        return out;
    }

    public static boolean deleteAppointment(long id) {
        Appointment removed = appointments.remove(id);
        if (removed != null) addAudit("admin","APPOINTMENT_DELETED","id="+id);
        return removed != null;
    }

    public static boolean updateAppointmentStatus(long id, String status) {
        Appointment a = appointments.get(id);
        if (a == null) return false;
        a.setStatus(status == null || status.isBlank() ? "scheduled" : status);
        addAudit("system", "APPOINTMENT_STATUS", "id=" + id + ", status=" + a.getStatus());
        return true;
    }

    // ========= OR routing =========
    public static List<Room> allRooms() { return new ArrayList<>(rooms.values()); }
    public static List<Source> allSources() { return new ArrayList<>(sources.values()); }
    public static Source findSource(Long id) { return sources.get(id); }

    public static Route setRoute(Long roomId, Long sourceId, String actor) {
        Route r = new Route(roomId, sourceId, now());
        routes.put(roomId, r);
        String rn = rooms.get(roomId) != null ? rooms.get(roomId).getName() : String.valueOf(roomId);
        String sn = sources.get(sourceId) != null ? sources.get(sourceId).getName() : String.valueOf(sourceId);
        addAudit(actor == null ? "system" : actor, "ROUTE_SET", rn + " -> " + sn);
        return r;
    }
    public static Route getRoute(Long roomId) { return routes.get(roomId); }
    public static List<Route> allRoutes() { return new ArrayList<>(routes.values()); }

    // ========= Recordings =========
    public static Recording addRecording(Recording rec) {
        long id = seqRecording.getAndIncrement();
        rec.setId(id);
        if (rec.getDatetime() == null) rec.setDatetime(now());
        recordings.put(id, rec);
        addAudit(rec.getDoctor() != null ? rec.getDoctor() : "system",
                "RECORDING_CREATED",
                "id=" + id + ", room=" + rec.getRoomId() + ", src=" + rec.getSourceId() + ", pt=" + rec.getPatient());
        return rec;
    }
    public static List<Recording> listRecordings() {
        List<Recording> out = new ArrayList<>(recordings.values());
        out.sort(Comparator.comparing(Recording::getDatetime));
        return out;
    }
    public static List<Recording> byDoctorRecordings(String doctor) {
        List<Recording> out = new ArrayList<>();
        for (Recording r : recordings.values())
            if (doctor != null && doctor.equalsIgnoreCase(r.getDoctor())) out.add(r);
        out.sort(Comparator.comparing(Recording::getDatetime));
        return out;
    }
    public static List<Recording> byPatientRecordings(String patient) {
        List<Recording> out = new ArrayList<>();
        for (Recording r : recordings.values())
            if (patient != null && patient.equalsIgnoreCase(r.getPatient())) out.add(r);
        out.sort(Comparator.comparing(Recording::getDatetime));
        return out;
    }

    // ========= Presets =========
    public static Preset addPreset(Preset p, String actor) {
        long id = seqPreset.getAndIncrement();
        p.setId(id);
        p.setDatetime(now());
        presets.put(id, p);
        addAudit(actor, "PRESET_CREATED", "id=" + id + ", name=" + p.getName());
        return p;
    }
    public static List<Preset> listPresets() {
        List<Preset> out = new ArrayList<>(presets.values());
        out.sort(Comparator.comparing(Preset::getDatetime));
        return out;
    }
    public static void applyPreset(Long id, String actor) {
        Preset p = presets.get(id);
        if (p == null) return;
        if (p.getMapping() != null) {
            for (Map.Entry<Long, Long> e : p.getMapping().entrySet()) {
                Long roomId = e.getKey(); Long sourceId = e.getValue();
                Source s = sources.get(sourceId);
                if (s != null && s.isOnline()) setRoute(roomId, sourceId, actor);
            }
        }
        addAudit(actor, "PRESET_APPLIED", "id=" + id + ", name=" + p.getName());
    }

    // ========= Annotations =========
    public static Annotation addAnnotation(Annotation a) {
        long id = seqAnnotation.getAndIncrement();
        a.setId(id);
        if (a.getDatetime() == null) a.setDatetime(now());
        annotations.put(id, a);
        addAudit(a.getAuthor() != null ? a.getAuthor() : "system",
                "ANNOTATION_ADDED", "recId=" + a.getRecordingId());
        return a;
    }
    public static List<Annotation> listAnnotationsByRecording(Long recordingId) {
        List<Annotation> out = new ArrayList<>();
        for (Annotation a : annotations.values())
            if (Objects.equals(a.getRecordingId(), recordingId)) out.add(a);
        out.sort(Comparator.comparing(Annotation::getDatetime));
        return out;
    }

    // ========= Patient Records (Hasta Geçmişi) =========

    /** Kayıt ekle (id yoksa atar, tarih yoksa now()) */
    public static PatientRecord addRecord(PatientRecord r) {
        if (r.getId() == 0) r.setId(seqRecord.getAndIncrement());
        if (r.getDatetime() == null || r.getDatetime().isBlank()) r.setDatetime(now());
        recordsByTc.computeIfAbsent(r.getTcNo(), k -> new ArrayList<>()).add(r);
        // İsteğe bağlı audit
        addAudit("system", "PATIENT_RECORD_ADDED", "tc=" + r.getTcNo() + ", id=" + r.getId());
        return r;
    }

    /** TC ile hastanın tüm geçmişini ver (yeni -> eski) */
    public static List<PatientRecord> listRecordsByTc(String tcNo) {
        List<PatientRecord> out = new ArrayList<>(recordsByTc.getOrDefault(tcNo, List.of()));
        out.sort(Comparator.comparing(PatientRecord::getDatetime).reversed());
        return out;
    }

    /** username'den TC'yi bularak geçmişi ver (hasta kendi geçmişi için) */
    public static List<PatientRecord> listRecordsByUsername(String username) {
        String tc = patientTcByUsername.get(username);
        if (tc == null) return List.of();
        return listRecordsByTc(tc);
    }
}
