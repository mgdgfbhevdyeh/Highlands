package com.fabricator77.highlands.block;

import com.fabricator77.highlands.Highlands;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;

public class BlockHighlandsStairs extends BlockStairs{

	public BlockHighlandsStairs(Block modelBlock, int meta){
		super(modelBlock, meta);
		this.setCreativeTab(Highlands.tabHighlands);
	}
}
