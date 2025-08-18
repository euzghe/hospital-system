-- Kullanıcılar
INSERT IGNORE INTO users (id, username, password, role, full_name, tc_no)
VALUES
  (1,'admin','1234','admin','Sistem Yöneticisi',NULL),
  (2,'doktor1','1234','doctor','Dr. Ahmet Yılmaz',NULL),
  (3,'hasta1','1234','patient','Ayşe Demir','12345678901');

-- Örnek randevu
INSERT IGNORE INTO appointments (id, doctor_username, patient_username, datetime, reason, status, created_at)
VALUES
  (1001,'doktor1','hasta1','2025-08-20T10:00','Kontrol','scheduled','2025-08-18T10:00');

-- Örnek hasta kaydı
INSERT IGNORE INTO patient_records (id, tc_no, patient_username, datetime, diagnosis, notes, labs)
VALUES
  (1,'12345678901','hasta1','2025-08-01 11:00','Appendektomi','Başarılı operasyon','Hb 13.5, WBC 9.2, CRP 4.1');
