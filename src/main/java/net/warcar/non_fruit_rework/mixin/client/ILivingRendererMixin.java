package net.warcar.non_fruit_rework.mixin.client;

import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LivingRenderer.class)
public interface ILivingRendererMixin<T extends LivingEntity, M extends EntityModel<T>> {
    @Accessor("model")
    void setModel(M model);
}
