package net.diamonddev.enderism.block;

import net.diamonddev.enderism.util.EnderismUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

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

                // Handle Goofy Teleport
                EnderismUtil.chorusTeleport(player, 16, 32.0);

                // Decrement Property
                int i = state.get(SLICES);
                if (i != 1) {
                    world.setBlockState(pos, state.with(SLICES, i - 1));
                } else {
                    world.removeBlock(pos, false);
                }
            }
        }
        return ActionResult.CONSUME;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Stream.of(
                Block.createCuboidShape(2, 5, 2, 14, 6, 14),
                Block.createCuboidShape(1, 4, 1, 15, 5, 15),
                Block.createCuboidShape(2, 0, 2, 14, 4, 14)
        ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    }
}
