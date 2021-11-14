package net.brian.islandcore.common.persistent;

import dev.reactant.reactant.extra.command.PermissionRoot;
import io.github.clayclaw.islandcore.IslandCore;
import io.lumine.mythic.utils.menu.MenuData;
import net.brian.islandcore.IslandCropsAndLiveStocks;
import org.bukkit.block.Block;
import org.bukkit.metadata.LazyMetadataValue;
import org.bukkit.metadata.MetadataValue;

import java.util.List;

public class BlockMeta {

    private static final IslandCore plugin = IslandCore.getInstance();

    public static String readUUID(Block block){
        return readString(block,"island_uuid");
    }

    public static void setData(Block block,String id,Object object){
        block.setMetadata(id,new LazyMetadataValue(plugin,()->object));
    }

    public static String readString(Block block,String id){
        List<MetadataValue> values = block.getMetadata(id);
        return values.size() > 0 ? values.get(0).asString():null;
    }

    public static Integer readInt(Block block,String id){
        List<MetadataValue> values = block.getMetadata(id);
        return values.size() > 0 ? values.get(0).asInt():null;
    }

}
