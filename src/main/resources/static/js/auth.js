const API = '/api/auth';

function salvarToken(token) {
    localStorage.setItem('greenday_token', token);
}

document.getElementById('form-login')?.addEventListener('submit', async (e) => {
    e.preventDefault();
    const email = document.getElementById('email').value;
    const senha = document.getElementById('senha').value;

    const resp = await fetch(`${API}/login`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email, senha })
    });

    if (resp.ok) {
        const data = await resp.json();
        salvarToken(data.token);
        window.location.href = '/dashboard';
    } else {
        alert('Email ou senha invalidos.');
    }
});

document.getElementById('form-cadastro')?.addEventListener('submit', async (e) => {
    e.preventDefault();
    const nome = document.getElementById('nome').value;
    const email = document.getElementById('email').value;
    const telefone = document.getElementById('telefone').value;
    const senha = document.getElementById('senha').value;

    const resp = await fetch(`${API}/cadastro`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ nome, email, telefone, senha })
    });

    if (resp.ok) {
        window.location.href = '/login';
    } else {
        const erro = await resp.json();
        alert(erro.mensagem || 'Erro ao cadastrar.');
    }
});
