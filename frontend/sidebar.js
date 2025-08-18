// frontend/sidebar.js  (v4)
// Amaç: Sidebar hep kalsın. Nav linklerine tıklayınca tam sayfa yerine
// sadece içerik (.content) değişsin. Geri/ileri tuşları da çalışır.

(function () {
  const PAGE_KEYS = ['admin','doctor','patient','or','sources','presets','recordings','audit'];

  function deriveKeyFromUrl(url) {
    try {
      const name = (url || location.pathname).split('/').pop().split('.')[0].toLowerCase();
      return PAGE_KEYS.includes(name) ? name : 'admin';
    } catch { return 'admin'; }
  }

  function setActive(navEl, key) {
    navEl.querySelectorAll('a').forEach(a => a.classList.toggle('active', a.dataset.key === key));
  }

  async function loadIntoContent(url, key) {
    const res = await fetch(url, { cache: 'no-store' });
    const html = await res.text();
    const doc = new DOMParser().parseFromString(html, 'text/html');

    // Yalnızca <body> içeriğini al; içerdeki scriptleri kopyalama (çifte çalışmasın)
    doc.body.querySelectorAll('script').forEach(s => s.remove());

    const main = document.querySelector('.content');
    if (!main) return location.href = url; // güvenli kaçış

    main.innerHTML = '';
    Array.from(doc.body.childNodes).forEach(n => main.appendChild(n.cloneNode(true)));

    document.title = doc.title || document.title;
    history.pushState({ url, key }, '', url);

    const nav = document.querySelector('.nav');
    if (nav) setActive(nav, key);

    window.scrollTo(0, 0);
  }

  function buildSidebar(activeKey) {
    const u = JSON.parse(localStorage.getItem('user') || 'null');
    if (!u) { location.href = 'login.html'; return; }
    const role = (u.role || 'admin').toLowerCase();

    // Body'yi iskelete çevir
    const body = document.body;
    const oldNodes = [];
    while (body.firstChild) { oldNodes.push(body.firstChild); body.removeChild(body.firstChild); }

    const layout = document.createElement('div'); layout.className = 'layout';
    const aside  = document.createElement('aside'); aside.className = 'sidebar';
    const main   = document.createElement('main'); main.className  = 'content';

    const brand = document.createElement('div'); brand.className = 'brand'; brand.textContent = '🏥 Mini NuCLeUS';
    const userEl = document.createElement('div'); userEl.className = 'user'; userEl.textContent = `@${u.username} • ${role}`;

    const nav = document.createElement('nav'); nav.className = 'nav';
    const MAP = {
      admin: [
        {key:'admin',      label:'Admin Paneli', href:'admin.html'},
        {key:'or',         label:'OR Router',    href:'or.html'},
        {key:'sources',    label:'Sources',      href:'sources.html'},
        {key:'presets',    label:'Presets',      href:'presets.html'},
        {key:'recordings', label:'Recordings',   href:'recordings.html'},
        {key:'audit',      label:'Audit Log',    href:'audit.html'},
      ],
      doctor: [
        {key:'doctor',     label:'Doktor Paneli', href:'doctor.html'},
        {key:'recordings', label:'Recordings',    href:'recordings.html'},
      ],
      patient: [
        {key:'patient',    label:'Hasta Paneli',  href:'patient.html'},
        {key:'recordings', label:'Recordings',    href:'recordings.html'},
      ],
    };
    const items = MAP[role] || MAP.admin;

    items.forEach(it => {
      const a = document.createElement('a');
      a.href = it.href;
      a.textContent = it.label;
      a.dataset.key = it.key;
      if (activeKey === it.key) a.classList.add('active');
      nav.appendChild(a);
    });

    const spacer = document.createElement('div'); spacer.className = 'spacer';

    const logout = document.createElement('a');
    logout.className = 'logout';
    logout.href = 'logout.html';
    logout.textContent = 'Çıkış';
    logout.addEventListener('click', ()=>{ try { localStorage.removeItem('user'); sessionStorage.clear(); } catch{} });

    aside.appendChild(brand);
    aside.appendChild(userEl);
    aside.appendChild(nav);
    aside.appendChild(spacer);
    aside.appendChild(logout);

    // Eski içerik sağ tarafa
    oldNodes.forEach(n => main.appendChild(n));

    layout.appendChild(aside);
    layout.appendChild(main);
    body.appendChild(layout);

    // DELEGASYON: Sidebar içindeki nav linklerine tek yerden intercept
    aside.addEventListener('click', async (e) => {
      const a = e.target.closest('.nav a');
      if (!a) return;
      e.preventDefault();
      const href = a.getAttribute('href');
      const key  = a.dataset.key || deriveKeyFromUrl(href);
      try { await loadIntoContent(href, key); }
      catch { location.href = href; }
    });

    // Geri/ileri
    window.addEventListener('popstate', async (e)=>{
      const url = (e.state && e.state.url) || location.pathname;
      const key = (e.state && e.state.key) || deriveKeyFromUrl(url);
      try { await loadIntoContent(url, key); } catch {}
    });
  }

  // Dışa açık: istersen manuel çağır
  window.initSidebar = function(activeKey){
    if (document.querySelector('.layout')) return;
    buildSidebar(activeKey || deriveKeyFromUrl(location.pathname));
  };

  // Otomatik kurulum
  document.addEventListener('DOMContentLoaded', ()=>{
    if (!document.querySelector('.layout')) {
      buildSidebar(deriveKeyFromUrl(location.pathname));
    }
  });
})();
