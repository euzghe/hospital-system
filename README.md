HospitalSystem
Bu proje, temel bir hastane yönetim sisteminin demo uygulamasıdır. Spring Boot ve Java ile yazılmış bir backend (arka uç) ve HTML, CSS ve JavaScript ile oluşturulmuş basit bir frontend (ön yüz) içerir.

Özellikler
Hasta Paneli: Hastaların randevu oluşturmasına ve geçmiş kayıtlarını görüntülemesine olanak tanır.

Poliklinik ve Doktor Yönetimi: Backend, poliklinik ve doktor verilerini yönetir.

RESTful API: Frontend, backend ile RESTful API'ler aracılığıyla iletişim kurar.

Teknolojiler
Backend (Arka Uç):

Java: Uygulamanın ana programlama dili.

Spring Boot: Backend servisini hızlıca oluşturmak için kullanılan bir framework.

Maven: Proje yönetimi ve bağımlılık yönetimi aracı.

Frontend (Ön Yüz):

HTML5: Sayfa yapısı.

CSS3: Sayfa stilini belirler.

JavaScript: Sayfaya interaktif özellikler ekler ve backend ile iletişim kurar.

Kurulum ve Çalıştırma
1. Ön Gereksinimler

Java Development Kit (JDK) 17 veya üzeri

Maven

Bir IDE (IntelliJ IDEA, VS Code vb.)

2. Projeyi Klonlama

Projeyi GitHub'dan yerel makinenize indirin:

git clone https://github.com/KULLANICI_ADINIZ/hospital-system.git
cd hospital-system

Not: KULLANICI_ADINIZ kısmını kendi GitHub kullanıcı adınızla değiştirin.

3. Backend'i Çalıştırma

Terminalde projenin ana dizinindeyken aşağıdaki komutu kullanarak backend'i çalıştırın:

mvn spring-boot:run

Uygulama http://localhost:8080 adresinde başlayacaktır.

4. Frontend'i Çalıştırma

Frontend'i çalıştırmak için basit bir web sunucusu kullanmanız gerekir. Projenizin frontend dizinine gidin ve aşağıdaki komutu çalıştırın:

cd frontend
python3 -m http.server 8000

5. Uygulamaya Erişme

Tarayıcınızı açın ve aşağıdaki adrese gidin:

http://localhost:8000/patient.html

Artık hasta panelini görüntüleyebilir, poliklinik ve doktorları seçebilir ve randevu oluşturabilirsiniz.

Katkıda Bulunma
Bu projeye katkıda bulunmak isterseniz, lütfen bir pull request gönderin veya bir issue açın.

