# Hogwarts RPG — Unreal Engine 5

Projeto inicial em C++ para reconstruir o RPG como jogo de fases e missões em terceira pessoa.

## O que já está preparado

- Projeto Unreal Engine 5 com módulo C++.
- Personagem em terceira pessoa com câmera, movimentação WASD, mouse e salto.
- Base de projétil mágico sem custo de mana, com velocidade e dano configuráveis.
- Game Mode com cinco objetivos iniciais de missão.
- Configuração de controles.

## O que ainda precisa ser feito no Unreal Editor

1. Instalar uma versão compatível da Unreal Engine 5 e o Visual Studio com a carga de trabalho **Desenvolvimento de jogos com C++**.
2. Abrir `HogwartsRPG.uproject` e permitir a compilação do módulo.
3. Criar o mapa inicial e definir pontos de surgimento.
4. Criar Blueprints derivados do personagem e do projétil; atribuir a classe de projétil ao campo `SpellProjectileClass`.
5. Adicionar modelos 3D licenciados, esqueletos, animações, materiais, efeitos de feitiços, inimigos com vida e lógica de conclusão de missões.
6. Criar a interface de missão, inventário, diálogo e transições entre fases.

## Controles previstos

- **W/A/S/D:** movimentar.
- **Mouse:** girar a câmera.
- **Espaço:** saltar.
- **Botão esquerdo:** lançar feitiço (após configurar uma classe de projétil no Blueprint).

## Observação importante

Este é o esqueleto inicial do projeto, não uma versão final pronta para jogar. Os mapas, modelos, animações e efeitos visuais ainda precisam ser importados e configurados no Unreal Editor. O projeto não é um site estático e não será executado pela Vercel como o jogo antigo.
