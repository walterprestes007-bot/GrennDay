const params = new URLSearchParams(window.location.search);
const propriedadeId = params.get('propriedadeId');
const token = localStorage.getItem('greenday_token');
const headers = { Authorization: `Bearer ${token}`, 'Content-Type': 'application/json' };

async function carregarSensores() {
    const sensores = await fetch(`/api/propriedades/${propriedadeId}/sensores`, { headers }).then(r => r.json());
    document.getElementById('lista-sensores').innerHTML = sensores.map(s => `
        <div class="card">
            <h3>${s.nome}</h3>
            <p>${s.tipo}</p>
            <p>Unidade: ${s.unidadeMedida ?? '-'}</p>
        </div>`).join('');
}

document.getElementById('btn-novo-sensor')?.addEventListener('click', async () => {
    const nome = prompt('Nome do sensor:');
    const tipo = prompt('Tipo (UMIDADE_SOLO, TEMPERATURA, LUMINOSIDADE, PH_SOLO, CHUVA):');
    if (!nome || !tipo) return;
    await fetch(`/api/propriedades/${propriedadeId}/sensores`, { method: 'POST', headers, body: JSON.stringify({ nome, tipo }) });
    carregarSensores();
});

if (propriedadeId) carregarSensores();
