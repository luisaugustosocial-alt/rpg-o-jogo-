#pragma once

#include "CoreMinimal.h"
#include "GameFramework/Actor.h"
#include "HogwartsSpellProjectile.generated.h"

class USphereComponent;
class UProjectileMovementComponent;

UCLASS()
class HOGWARTSRPG_API AHogwartsSpellProjectile : public AActor
{
    GENERATED_BODY()

public:
    AHogwartsSpellProjectile();

protected:
    UPROPERTY(VisibleAnywhere, Category = "Feitico")
    TObjectPtr<USphereComponent> CollisionSphere;

    UPROPERTY(VisibleAnywhere, Category = "Feitico")
    TObjectPtr<UProjectileMovementComponent> ProjectileMovement;

    UPROPERTY(EditDefaultsOnly, Category = "Feitico")
    float Damage = 35.0f;

    UFUNCTION()
    void OnSpellHit(UPrimitiveComponent* HitComponent, AActor* OtherActor,
        UPrimitiveComponent* OtherComponent, FVector NormalImpulse, const FHitResult& Hit);
};
