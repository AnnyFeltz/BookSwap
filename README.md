# BookSwap: Troque Livros, Compartilhe Histórias 📚🔄

<p align="center">
  <img src="https://via.placeholder.com/600x200?text=Logo+do+BookSwap" alt="Logo BookSwap - Placeholder">
  </p>

## [cite_start]O Que É o BookSwap? [cite: 12]

[cite_start]O BookSwap é um site de trocas de livros com moedas digitais [cite: 13] (créditos) [cite_start]que podem ser utilizadas na troca e convertidas em dinheiro real[cite: 13].

## Para Que Serve e Qual Problema Busca Resolver?

[cite_start]O BookSwap busca resolver o problema de desperdício e inutilização de livros já terminados ou nos quais o leitor perdeu interesse, além do aumento no preço dos livros[cite: 27].

* [cite_start]**Para quem serve:** Consumidores de Livros Físicos [cite: 33] [cite_start]que não têm tempo ou dinheiro para comprar novos livros[cite: 34].
* [cite_start]**Impacto buscado:** Reduzir o impacto ambiental e a barreira capital literária[cite: 29].
* [cite_start]**Solução:** Fornecer um site de trocas para livros onde o usuário pode se cadastrar, colocar seus livros para troca, e outros usuários podem iniciar a troca e negociar seus termos[cite: 30]. [cite_start]O sistema possibilita a troca de livros para pessoas que não possuem comunicação e contato suficiente para fazer por conta própria[cite: 40].

## Como o Sistema Vai Funcionar (Fluxo Principal)

[cite_start]O produto possui um layout intuitivo, dinâmico, otimizado e eficaz, tornando a troca de livros dinâmica e possibilitando a negociação para conseguir livros de forma mais barata[cite: 44]. [cite_start]O ciclo de tarefas principal envolve agendamento de trocas, negociação de livros, junto de gerenciamento e compra de moedas virtuais para uso em trocas[cite: 17].

1.  [cite_start]**Inventário e Pesquisa:** O usuário cadastra seus livros para troca no sistema (Registro de Livros [cite: 244]). [cite_start]Outros usuários podem pesquisar e visualizar os livros disponíveis no catálogo (Visualização de Livros [cite: 244]).
2.  [cite_start]**Início da Troca:** Um usuário (Usuário A) seleciona o livro que deseja trocar e propõe uma troca[cite: 279]. [cite_start]O sistema deve notificar o dono do livro (Usuário B)[cite: 280].
3.  [cite_start]**Negociação:** Os usuários podem entrar em um **Chat de mensagens** para enviar e receber mensagens, e discutir os detalhes da troca[cite: 233, 236, 282]. [cite_start]Negociações podem levar dias, mas possuem um limite de 5[cite: 18].
4.  [cite_start]**Uso de Créditos:** Durante a negociação, os usuários podem fornecer seus créditos para equiparar a troca[cite: 242]. [cite_start]Os créditos são adquiridos via pacotes pré-definidos[cite: 217, 284].
5.  [cite_start]**Conclusão da Troca:** O usuário deve ter a opção de aceitar ou rejeitar a proposta[cite: 283]. [cite_start]O status muda para **"Troca aceita"** quando ambos concordam, e para **"Troca concluída"** quando ambas as partes confirmam o recebimento dos livros trocados[cite: 281].

## [cite_start]Atores do Sistema [cite: 249]

O sistema possui dois atores principais:

1.  [cite_start]**Usuário**[cite: 258, 264].
2.  [cite_start]**Moderador**[cite: 251, 269].

## Funções dos Atores

### Usuário (Ator Principal)

| Função | O que o sistema permite (Caso de Uso) |
| :--- | :--- |
| **Acessar o Perfil** | [cite_start]Faz login, registra e mantém seus dados atualizados (Login/Registro)[cite: 258, 265]. [cite_start]Permite a manutenção dos dados essenciais de conta (Manter Conta) [cite: 259] [cite_start]e a atualização de informações públicas e de preferência (Editar Perfil)[cite: 260]. |
| **Inventário** | [cite_start]Adiciona, edita e gerencia os livros que possui e deseja disponibilizar para troca (Manter Livros)[cite: 261, 266]. [cite_start]Permite a pesquisa e a navegação pelo catálogo (Visualizar Livros)[cite: 262]. |
| **Transações e Negociações** | [cite_start]Inicia, recebe, negocia e aceita/rejeita propostas de troca[cite: 267]. [cite_start]Processo central que gerencia a negociação (Realizar troca)[cite: 264]. [cite_start]Permite utilizar créditos como pagamento ou oferta (Oferecer créditos)[cite: 263]. |
| **Financeiro** | [cite_start]Compra moedas ou as ganha como recompensa por trocas bem-sucedidas[cite: 268]. [cite_start]Monitora o saldo e o histórico de transações[cite: 269]. |

### Moderador

| Função | O que o sistema permite |
| :--- | :--- |
| **Gestão de Conteúdo** | [cite_start]Monitora e remove conteúdos inadequados (livros, imagens e informações de perfil) que violem as regras[cite: 270]. [cite_start]Deve poder buscar, filtrar, e remover permanentemente um livro do catálogo[cite: 287]. |
| **Governança do Sistema** | [cite_start]Suspende ou bane usuários por má conduta[cite: 271]. [cite_start]Deve ter opções para suspender o acesso temporariamente ou banir permanentemente, exigindo um motivo[cite: 293, 294]. |
| **Resolução de Conflitos** | [cite_start]Intervém em disputas de troca, decidindo o resultado final e ajustando saldos de moedas/inventário[cite: 272]. [cite_start]Deve ter acesso a um histórico completo de mensagens e eventos da troca contestada[cite: 289]. |
