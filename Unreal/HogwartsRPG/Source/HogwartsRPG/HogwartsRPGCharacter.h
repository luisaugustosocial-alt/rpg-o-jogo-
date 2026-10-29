#pragma once

#include "CoreMinimal.h"
#include "GameFramework/Character.h"
#include "HogwartsRPGCharacter.generated.h"

class USpringArmComponent;
class UCameraComponent;
class AHogwartsSpellProjectile;

UCLASS()
class HOGWARTSRPG_API AHogwartsRPGCharacter : public ACharacter
{
    GENERATED_BODY()

public:
    AHogwartsRPGCharacter();

protected:
    virtual void BeginPlay() override;
    virtual void SetupPlayerInputComponent(UInputComponent* PlayerInputComponent) override;

private:
    void MoveForward(float Value);
    void MoveRight(float Value);
    void CastSpell();

    UPROPERTY(VisibleAnywhere, Category = "Camera")
    TObjectPtr<USpringArmComponent> CameraBoom;

    UPROPERTY(VisibleAnywhere, Category = "Camera")
    TObjectPtr<UCameraComponent> FollowCamera;

    UPROPERTY(EditDefaultsOnly, Category = "Feiticos")
    TSubclassOf<AHogwartsSpellProjectile> SpellProjectileClass;

    UPROPERTY(EditDefaultsOnly, Category = "Feiticos")
    float SpellSpawnDistance = 110.0f;
};
