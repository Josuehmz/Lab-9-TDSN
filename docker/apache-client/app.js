const out = document.getElementById('out');

document.getElementById('btn').addEventListener('click', async () => {
    out.textContent = 'Cargando…';
    try {
        const res = await fetch('/api/hello', {
            headers: {
                Authorization: 'Basic ' + btoa('admin:admin123'),
            },
        });
        const text = await res.text();
        out.textContent = res.ok ? text : `HTTP ${res.status}: ${text}`;
    } catch (e) {
        out.textContent = 'Error: ' + e.message;
    }
});
