# Hogwarts RPG — Java + LibGDX

Protótipo 3D para computador, criado em Java usando LibGDX e LWJGL3.

## Controles
- **W A S D**: mover
- **Mouse**: olhar ao redor
- **E** ou **clique esquerdo**: lançar feitiço ilimitado
- **F**: avançar para a próxima missão quando todos os inimigos forem derrotados
- **Esc**: liberar/prender o mouse

## Missões iniciais
1. Pátio de Hogwarts: derrotar o Trasgo.
2. Biblioteca: derrotar o Aranhido.
3. Corredor: derrotar o Dementador.
4. Treino final: enfrentar dois inimigos.
5. Conclusão da sequência inicial.

## Requisitos
- JDK 17
- Gradle 8 ou compatível
- Windows, macOS ou Linux com suporte gráfico OpenGL

## Executar
Na pasta `Java/HogwartsRPG`, execute:

```bash
gradle :lwjgl3:run
```

O jogo abre numa janela desktop. Ele não roda diretamente na Vercel como o jogo HTML anterior.

## Estado atual
É um protótipo funcional de gameplay com cenário e inimigos feitos com formas 3D, feitiços, movimentação e missões. Ainda não inclui modelos cinematográficos, animações esqueléticas completas, vozes, menus finais ou mapas detalhados. Esses recursos exigem assets 3D próprios/licenciados e trabalho adicional de integração.
