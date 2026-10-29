#include "HogwartsSpellProjectile.h"
#include "Components/SphereComponent.h"
#include "GameFramework/ProjectileMovementComponent.h"
#include "Kismet/GameplayStatics.h"

AHogwartsSpellProjectile::AHogwartsSpellProjectile()
{
    PrimaryActorTick.bCanEverTick = false;

    CollisionSphere = CreateDefaultSubobject<USphereComponent>(TEXT("CollisionSphere"));
    CollisionSphere->InitSphereRadius(16.0f);
    CollisionSphere->SetCollisionProfileName(TEXT("BlockAllDynamic"));
    RootComponent = CollisionSphere;

    ProjectileMovement = CreateDefaultSubobject<UProjectileMovementComponent>(TEXT("ProjectileMovement"));
    ProjectileMovement->InitialSpeed = 1700.0f;
    ProjectileMovement->MaxSpeed = 1700.0f;
    ProjectileMovement->bRotationFollowsVelocity = true;
    ProjectileMovement->ProjectileGravityScale = 0.0f;

    InitialLifeSpan = 3.0f;
    CollisionSphere->OnComponentHit.AddDynamic(this, &AHogwartsSpellProjectile::OnSpellHit);
}

void AHogwartsSpellProjectile::OnSpellHit(UPrimitiveComponent* HitComponent, AActor* OtherActor,
    UPrimitiveComponent* OtherComponent, FVector NormalImpulse, const FHitResult& Hit)
{
    if (OtherActor && OtherActor != this && OtherActor != GetOwner())
    {
        UGameplayStatics::ApplyDamage(OtherActor, Damage, GetInstigatorController(), this, nullptr);
        Destroy();
    }
}
