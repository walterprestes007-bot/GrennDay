const params = new URLSearchParams(window.location.search);
const propriedadeId = params.get('propriedadeId');
const token = localStorage.getItem('greenday_token');
const headers = { Authorization: `Bearer ${token}`, 'Content-Type': 'application/json' };

async function carregarCulturas() {
    const culturas = await fetch(`/api/propriedades/${propriedadeId}/culturas`, { headers }).then(r => r.json());
    const tbody = document.querySelector('#tabela-culturas tbody');
    tbody.innerHTML = culturas.map(c => `
        <tr>
            <td>${c.nome}</td><td>${c.variedade ?? '-'}</td>
            <td>${c.dataPlantio ?? '-'}</td><td>${c.previsaoColheita ?? '-'}</td>
            <td>${c.areaOcupadaHectares ?? '-'}</td>
        </tr>`).join('');
}

document.getElementById('btn-nova-cultura')?.addEventListener('click', async () => {
    const nome = prompt('Nome da cultura:');
    if (!nome) return;
    await fetch(`/api/propriedades/${propriedadeId}/culturas`, { method: 'POST', headers, body: JSON.stringify({ nome }) });
    carregarCulturas();
});

if (propriedadeId) carregarCulturas();
