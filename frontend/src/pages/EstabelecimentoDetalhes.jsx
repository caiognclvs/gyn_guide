import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import api from '../services/api';

export default function EstabelecimentoDetalhes() {
    const { id } = useParams();
    const [estabelecimento, setEstabelecimento] = useState(null);
    const [avaliacoes, setAvaliacoes] = useState([]);
    const [loading, setLoading] = useState(true);
    const [nota, setNota] = useState(5);
    const [comentario, setComentario] = useState('');
    const usuario = JSON.parse(localStorage.getItem('usuario') || 'null');

    useEffect(() => {
        async function load() {
            setLoading(true);
            try {
                // tentativa de obter estabelecimento com avaliacoes juntas
                const res = await api.get(`/estabelecimentos/${id}`);
                const data = res.data || {};
                // aceitar diferentes formatos: {estabelecimento, avaliacoes} ou diretamente objeto com avaliacoes
                const estab = data.estabelecimento ?? data;
                setEstabelecimento(estab);

                if (data.avaliacoes) {
                    setAvaliacoes([...data.avaliacoes].sort((a,b) => new Date(b.data) - new Date(a.data)));
                } else {
                    // fallback: buscar avaliações específicas
                    const r2 = await api.get(`/avaliacoes/estabelecimento/${id}`);
                    setAvaliacoes((r2.data || []).sort((a,b) => new Date(b.data) - new Date(a.data)));
                }
            } catch (err) {
                console.error('Erro ao carregar estabelecimento/avaliacoes', err);
            } finally {
                setLoading(false);
            }
        }
        load();
    }, [id]);

    async function handleEnviarAvaliacao(e) {
        e.preventDefault();
        if (!usuario || usuario.tipoUsuario !== 'PESSOA_FISICA') return;
        try {
            const payload = {
                autorId: usuario.id,
                estabelecimentoId: Number(id),
                nota: Number(nota),
                comentario: comentario || null
            };
            await api.post('/avaliacoes', payload);
            // recarregar avaliacoes
            const r = await api.get(`/avaliacoes/estabelecimento/${id}`);
            setAvaliacoes((r.data || []).sort((a,b) => new Date(b.data) - new Date(a.data)));
            setComentario('');
            setNota(5);
        } catch (err) {
            console.error('Erro ao enviar avaliação', err);
            alert('Erro ao enviar avaliação');
        }
    }

    if (loading) return <div>Carregando...</div>;
    if (!estabelecimento) return <div>Estabelecimento não encontrado.</div>;

    return (
        <div>
            <h1>{estabelecimento.nome}</h1>
            {estabelecimento.nomeFantasia && <h2>{estabelecimento.nomeFantasia}</h2>}
            {estabelecimento.imagemUrl && <img src={estabelecimento.imagemUrl} alt={estabelecimento.nome} style={{maxWidth: '400px'}} />}
            <p><strong>Endereço:</strong> {estabelecimento.endereco}</p>
            <p><strong>Descrição:</strong> {estabelecimento.descricao}</p>

            <section>
                <h3>Avaliações</h3>
                {avaliacoes.length === 0 ? (
                    <p>Nenhuma avaliacao registrada ainda</p>
                ) : (
                    <ul>
                        {avaliacoes.map(av => (
                            <li key={av.id}>
                                <div>
                                                <strong>{av.autorNome ?? av.autor?.nome ?? 'Usuário'}</strong> — {new Date(av.dataAvaliacao).toLocaleString()}
                                </div>
                                <div>Nota: {av.nota}</div>
                                {av.texto && <p>{av.texto}</p>}
                            </li>
                        ))}
                    </ul>
                )}
            </section>

            {usuario && usuario.tipoUsuario === 'PESSOA_FISICA' && (
                <section>
                    <h3>Registrar Avaliação</h3>
                    <form onSubmit={handleEnviarAvaliacao}>
                        <label>
                            Nota:
                            <select value={nota} onChange={e => setNota(e.target.value)}>
                                {[5,4,3,2,1].map(n => <option key={n} value={n}>{n}</option>)}
                            </select>
                        </label>
                        <br />
                        <label>
                            Comentário:
                            <br />
                            <textarea value={comentario} onChange={e => setComentario(e.target.value)} rows={4} />
                        </label>
                        <br />
                        <button type="submit">Enviar avaliação</button>
                    </form>
                </section>
            )}
        </div>
    );
}
