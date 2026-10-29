#pragma once

#include "CoreMinimal.h"
#include "GameFramework/GameModeBase.h"
#include "HogwartsRPGGameMode.generated.h"

UCLASS()
class HOGWARTSRPG_API AHogwartsRPGGameMode : public AGameModeBase
{
    GENERATED_BODY()

public:
    AHogwartsRPGGameMode();

    UFUNCTION(BlueprintCallable, Category = "Missao")
    FString GetCurrentMission() const;

    UFUNCTION(BlueprintCallable, Category = "Missao")
    void AdvanceMission();

    UFUNCTION(BlueprintCallable, Category = "Missao")
    int32 GetMissionNumber() const;

private:
    UPROPERTY(EditDefaultsOnly, Category = "Missoes")
    TArray<FString> MissionObjectives;

    UPROPERTY(VisibleAnywhere, Category = "Missoes")
    int32 CurrentMissionIndex = 0;
};
