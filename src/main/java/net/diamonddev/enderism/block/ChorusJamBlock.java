package net.diamonddev.enderism.block;

import net.diamonddev.libgenetics.common.api.v1.block.model.HorizontalRotationalBlock;
import net.minecraft.util.shape.VoxelShape;

public class ChorusJamBlock extends HorizontalRotationalBlock {
    public ChorusJamBlock(Settings settings) {
        super(settings);
    }

    public VoxelShape getVoxelShape() {
        return
    }
    @Override
    public VoxelShape getNorthShape() {
        return getVoxelShape();
    }
    @Override
    public VoxelShape getEastShape() {
        return getVoxelShape();
    }
    @Override
    public VoxelShape getSouthShape() {
        return getVoxelShape();
    }
    @Override
    public VoxelShape getWestShape() {
        return getVoxelShape();
    }
}
