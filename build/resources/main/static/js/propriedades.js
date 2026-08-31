const token = localStorage.getItem('greenday_token');
const headers = { Authorization: `Bearer ${token}`, 'Content-Type': 'application/json' };

async function carregarPropriedades() {
    const propriedades = await fetch('/api/propriedades', { headers }).then(r => r.json());
    document.getElementById('lista-propriedades').innerHTML = propriedades.map(p => `
        <div class="card">
            <h3>${p.nome}</h3>
            <p>${p.endereco ?? ''}</p>
            <p>${p.areaHectares ?? '-'} ha</p>
            <a href="/dashboard?propriedadeId=${p.id}">Ver painel</a>
        </div>`).join('');
}

document.getElementById('btn-nova-propriedade')?.addEventListener('click', async () => {
    const nome = prompt('Nome da propriedade:');
    if (!nome) return;
    await fetch('/api/propriedades', { method: 'POST', headers, body: JSON.stringify({ nome }) });
    carregarPropriedades();
});

carregarPropriedades();
