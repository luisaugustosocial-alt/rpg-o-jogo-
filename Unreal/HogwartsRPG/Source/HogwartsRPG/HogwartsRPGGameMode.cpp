#include "HogwartsRPGGameMode.h"
#include "HogwartsRPGCharacter.h"

AHogwartsRPGGameMode::AHogwartsRPGGameMode()
{
    DefaultPawnClass = AHogwartsRPGCharacter::StaticClass();

    MissionObjectives = {
        TEXT("Missao 1: Explore o patio de Hogwarts."),
        TEXT("Missao 2: Encontre o caminho para a biblioteca."),
        TEXT("Missao 3: Aprenda a lancar seu primeiro feitico."),
        TEXT("Missao 4: Sobreviva ao encontro na Floresta Proibida."),
        TEXT("Missao 5: Volte ao castelo e conclua a missao.")
    };
}

FString AHogwartsRPGGameMode::GetCurrentMission() const
{
    if (MissionObjectives.IsValidIndex(CurrentMissionIndex))
    {
        return MissionObjectives[CurrentMissionIndex];
    }
    return TEXT("Todas as missoes desta etapa foram concluidas.");
}

void AHogwartsRPGGameMode::AdvanceMission()
{
    if (CurrentMissionIndex < MissionObjectives.Num())
    {
        ++CurrentMissionIndex;
    }
}

int32 AHogwartsRPGGameMode::GetMissionNumber() const
{
    return FMath::Min(CurrentMissionIndex + 1, MissionObjectives.Num());
}
