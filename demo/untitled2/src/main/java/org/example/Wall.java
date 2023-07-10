package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Wall implements Structure{

    private List<Block> blocks;

    // zwraca dowolny element o podanym kolorze
    @Override
    public Optional<Block> findBlockByColor(String color) {



            for(Block block : blocks){

                Optional<Block> myColor = findMyColor(block, color);
                if(myColor.isPresent()){
                    return myColor;
                }

            }
            return Optional.empty();

    }

    private Optional<Block> findMyColor(Block block, String color) {

        if(block.getColor().equals(color)){
            return Optional.of(block);
        }
    return Optional.empty();
    }

    // zwraca wszystkie elementy z danego materiału
    @Override
    public List<Block> findBlocksByMaterial(String material) {

        List<Block> myBlockListMaterials = new ArrayList<>();
        for(Block block : blocks){

            Optional<Block> block1 = findMyMaterial(block, material);
            if(block1.isPresent()){
                Block blockMaterialCorrect = block1.get();
                myBlockListMaterials.add(blockMaterialCorrect);
            }

        }

        return myBlockListMaterials;
    }

    private Optional<Block> findMyMaterial(Block block, String material) {
        if(block.getMaterial().equals(material)){
            return Optional.of(block);
        }
        return Optional.empty();
    }

    @Override
    public int count() {
        return blocks.size();
    }

}
