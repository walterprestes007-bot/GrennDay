// Ponto de partida para o painel: busca o resumo da propriedade selecionada
// e renderiza os cartoes de indicadores + lista de alertas.
async function carregarDashboard(propriedadeId) {
    const token = localStorage.getItem('greenday_token');
    const headers = { Authorization: `Bearer ${token}` };

    const resumo = await fetch(`/api/propriedades/${propriedadeId}/dashboard`, { headers }).then(r => r.json());
    const cards = document.getElementById('cards-resumo');
    cards.innerHTML = `
        <div class="card"><h3>Culturas</h3><p>${resumo.totalCulturas}</p></div>
        <div class="card"><h3>Sensores</h3><p>${resumo.totalSensores}</p></div>
        <div class="card"><h3>Alertas nao lidos</h3><p>${resumo.alertasNaoLidos}</p></div>
        <div class="card"><h3>Irrigacoes</h3><p>${resumo.totalIrrigacoes}</p></div>
    `;

    const alertas = await fetch(`/api/propriedades/${propriedadeId}/alertas/nao-lidos`, { headers }).then(r => r.json());
    document.getElementById('lista-alertas').innerHTML =
        alertas.map(a => `<li>[${a.severidade}] ${a.mensagem}</li>`).join('');
}

document.getElementById('btn-sair')?.addEventListener('click', () => {
    localStorage.removeItem('greenday_token');
    window.location.href = '/login';
});
