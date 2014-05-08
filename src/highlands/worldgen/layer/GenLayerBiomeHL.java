package highlands.worldgen.layer;

import org.apache.logging.log4j.Level;

import highlands.Highlands;
import highlands.Logs;
import highlands.api.HighlandsBiomes;

import com.google.common.collect.ObjectArrays;

import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerEdge;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerBiomeHL extends GenLayer
{
    private BiomeGenBase[] heatBiomes; // field_151623_c
    private BiomeGenBase[] warmBiomes; // field_151621_d
    private BiomeGenBase[] coolBiomes; // field_151622_e
    private BiomeGenBase[] iceBiomes;  // field_151620_f

    public GenLayerBiomeHL(long par1, GenLayer par3GenLayer, WorldType par4WorldType)
    {
        super(par1);
        // Heat biomes
        this.heatBiomes = new BiomeGenBase[] {
        	BiomeGenBase.desert, BiomeGenBase.desert, BiomeGenBase.desert,
        	BiomeGenBase.savanna, BiomeGenBase.savanna, BiomeGenBase.plains
        };
        // Warm biomes
        this.warmBiomes = new BiomeGenBase[] {
        	BiomeGenBase.forest, BiomeGenBase.roofedForest, BiomeGenBase.extremeHills, BiomeGenBase.plains,
        	BiomeGenBase.birchForest, BiomeGenBase.swampland, BiomeGenBase.jungle // mod add jungle
        };
        // Cool biomes
        this.coolBiomes = new BiomeGenBase[] {
        	BiomeGenBase.forest, BiomeGenBase.extremeHills, BiomeGenBase.taiga, BiomeGenBase.plains
        };
        // Ice Biomes
        this.iceBiomes = new BiomeGenBase[] {
        	BiomeGenBase.icePlains, BiomeGenBase.icePlains, BiomeGenBase.icePlains, BiomeGenBase.coldTaiga
        };
        this.parent = par3GenLayer;

        if (par4WorldType == WorldType.DEFAULT_1_1)
        {
            this.heatBiomes = new BiomeGenBase[] {BiomeGenBase.desert, BiomeGenBase.forest, BiomeGenBase.extremeHills, BiomeGenBase.swampland, BiomeGenBase.plains, BiomeGenBase.taiga};
        }
        
        // mod
        if (HighlandsBiomes.biomesForHighlands.size() == 0) {
        	Logs.log(Level.FATAL, "[Highlands] no biomes loaded");
        	return;
        }
        
        addBiome(HighlandsBiomes.woodsMountains, Mode.WARM);
        addBiome(HighlandsBiomes.highlandsb, Mode.WARM);
        addBiome(HighlandsBiomes.tundra, Mode.ICE);
        addBiome(HighlandsBiomes.cliffs, Mode.WARM);
        addBiome(HighlandsBiomes.pinelands, Mode.WARM);
        addBiome(HighlandsBiomes.autumnForest, Mode.WARM);
        addBiome(HighlandsBiomes.alps, Mode.ICE);
        addBiome(HighlandsBiomes.tallPineForest, Mode.WARM);
        addBiome(HighlandsBiomes.meadow, Mode.WARM);
        addBiome(HighlandsBiomes.savannah, Mode.HEAT);       
        addBiome(HighlandsBiomes.tropics, Mode.HEAT);
        addBiome(HighlandsBiomes.outback, Mode.HEAT);
        addBiome(HighlandsBiomes.woodlands, Mode.WARM);
        addBiome(HighlandsBiomes.bog, Mode.COOL);
        addBiome(HighlandsBiomes.redwoodForest, Mode.WARM);
        addBiome(HighlandsBiomes.dunes, Mode.HEAT);
        addBiome(HighlandsBiomes.lowlands, Mode.WARM);
        addBiome(HighlandsBiomes.sahel, Mode.WARM);
        addBiome(HighlandsBiomes.birchHills, Mode.WARM);
        addBiome(HighlandsBiomes.tropicalIslands, Mode.HEAT);
        addBiome(HighlandsBiomes.rainforest, Mode.HEAT);
        addBiome(HighlandsBiomes.estuary, Mode.WARM);
        addBiome(HighlandsBiomes.badlands, Mode.HEAT);
        addBiome(HighlandsBiomes.flyingMountains, Mode.WARM);
        addBiome(HighlandsBiomes.snowMountains, Mode.ICE);
        addBiome(HighlandsBiomes.rockMountains, Mode.WARM);
        addBiome(HighlandsBiomes.desertMountains, Mode.WARM);
        addBiome(HighlandsBiomes.steppe, Mode.ICE);
        addBiome(HighlandsBiomes.glacier, Mode.ICE);
        
        //if (ModSettings.biomesOPlentyInstalled) {
        	// BopBiomes.addBoPbiomes(this);
        //}
    }

    /**
     * Returns a list of integer values generated by this layer. These may be interpreted as temperatures, rainfall
     * amounts, or biomeList[] indices based on the particular GenLayer subclass.
     */
    @Override
    public int[] getInts(int par1, int par2, int par3, int par4)
    {
        int[] aint = this.parent.getInts(par1, par2, par3, par4);
        int[] aint1 = IntCache.getIntCache(par3 * par4);

        for (int i1 = 0; i1 < par4; ++i1)
        {
            for (int j1 = 0; j1 < par3; ++j1)
            {
                this.initChunkSeed((long)(j1 + par1), (long)(i1 + par2));
                int k1 = aint[j1 + i1 * par3];
                int l1 = (k1 & 3840) >> 8;
                k1 &= -3841;
                
                // if (k1 < 0) k1 = 0; // vanilla bug ??

                if (isBiomeOceanic(k1))
                {
                	if (k1 == 0 && Highlands.improvedOceans) {
                		k1 = HighlandsBiomes.ocean2.biomeID;
                	}
                    aint1[j1 + i1 * par3] = k1;
                }
                else if (k1 == BiomeGenBase.mushroomIsland.biomeID)
                {
                    aint1[j1 + i1 * par3] = k1;
                }
                // plains
                else if (k1 == 1)
                {
                    if (l1 > 0)
                    {
                        if (this.nextInt(3) == 0)
                        {
                            aint1[j1 + i1 * par3] = BiomeGenBase.mesaPlateau.biomeID;
                        }
                        else
                        {
                            aint1[j1 + i1 * par3] = BiomeGenBase.mesaPlateau_F.biomeID;
                        }
                    }
                    else
                    {
                        aint1[j1 + i1 * par3] = this.heatBiomes[this.nextInt(this.heatBiomes.length)].biomeID;
                    }
                }
                // desert
                else if (k1 == 2)
                {
                	// Jungle if ???
                    //if (l1 > 0)
                    //{
                    //    aint1[j1 + i1 * par3] = BiomeGenBase.jungle.biomeID;
                    //}
                    //else
                    //{
                        aint1[j1 + i1 * par3] = this.warmBiomes[this.nextInt(this.warmBiomes.length)].biomeID;
                    //}
                }
                // extreme hills
                else if (k1 == 3)
                {
                    if (l1 > 0)
                    {
                        aint1[j1 + i1 * par3] = BiomeGenBase.megaTaiga.biomeID;
                    }
                    else
                    {
                        aint1[j1 + i1 * par3] = this.coolBiomes[this.nextInt(this.coolBiomes.length)].biomeID;
                    }
                }
                // forest
                else if (k1 == 4)
                {
                    aint1[j1 + i1 * par3] = this.iceBiomes[this.nextInt(this.iceBiomes.length)].biomeID;
                }
                else
                {
                	// put code for mod Island Biomes here ???
                    aint1[j1 + i1 * par3] = BiomeGenBase.mushroomIsland.biomeID;
                }
                
                if (k1 < 0 || aint1[j1 + i1 * par3] < 0) {
                	k1 = 0; // vanilla bug ??
                	aint1[j1 + i1 * par3] = k1;
                }
            }
        }

        return aint1;
    }
    
    // Custom mod
    public void addBiome (BiomeGenBase biome, Mode mode) {
    	switch (mode.ordinal())
        {
            case 1:
            	this.heatBiomes = ObjectArrays.concat(this.heatBiomes, biome); return;
            default:
            case 2:
            	this.warmBiomes = ObjectArrays.concat(this.warmBiomes, biome); return;
            case 3:
            	this.coolBiomes = ObjectArrays.concat(this.coolBiomes, biome); return;
            case 4:
            	this.iceBiomes = ObjectArrays.concat(this.iceBiomes, biome); return;
        }
    }
    
    /**
     * returns true if the biomeId is one of the various ocean biomes.
     */
    protected static boolean isBiomeOceanic(int biomeID)
    {
        return biomeID == BiomeGenBase.ocean.biomeID || biomeID == BiomeGenBase.deepOcean.biomeID || biomeID == BiomeGenBase.frozenOcean.biomeID ||  biomeID == HighlandsBiomes.ocean2.biomeID;
    }
    
    public static enum Mode
    {
    	HEAT,
    	WARM,
        COOL,
        ICE
    }
}
