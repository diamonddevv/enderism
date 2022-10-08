package net.diamonddev.enderism.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ChorusFruitPieBlock extends Block {

    private final int sliceCount;
    public static final IntProperty SLICES = IntProperty.of("slices", 1, 6);
    private final FoodComponent foodComponent;

    public ChorusFruitPieBlock(FoodComponent foodComponent, int slices, Settings settings) {
        super(settings);
        this.foodComponent = foodComponent; // I use a full food component, but only pull hunger and saturation from it.
        this.sliceCount = slices;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(SLICES);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(SLICES, this.sliceCount);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            if (player.canConsume(false) || player.getAbilities().creativeMode) {
                // Handle Food
                int hunger = this.foodComponent.getHunger();
                float saturation = this.foodComponent.getSaturationModifier();
                player.getHungerManager().add(hunger, saturation);

                // Decrement Property
                int i = state.get(SLICES);
                if (i > this.sliceCount) {
                    world.setBlockState(pos, state.with(SLICES, i - 1));
                } else {
                    world.removeBlock(pos, false);
                }
            }
        }
        return ActionResult.CONSUME;
    }
}
