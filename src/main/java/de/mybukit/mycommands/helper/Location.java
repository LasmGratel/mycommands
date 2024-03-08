package de.mybukkit.mycommands.helper;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChunkCoordinates;


public class Location {

    public int x;
    public int y;
    public int z;
    public double posX;
    public double posY;
    public double posZ;
    public int dimension;

    public Location(int x, int y, int z, int dimension) {
        this.x = x;
        this.y = y;
        this.z = z;

        this.posX = x;
        this.posY = y;
        this.posZ = z;

        this.dimension = dimension;
    }


    public Location(double posX, double posY, double posZ, int dimension) {
        init(posX, posY, posZ, dimension);
    }


    public Location(EntityPlayerMP player) {
        init(player.posX, player.posY, player.posZ, player.dimension);
    }


    public Location(EntityPlayerMP player, String s) {
        init(player.posX, player.posY, player.posZ, player.dimension);
    }


    public Location(String info) {
        String[] part = info.split("[,]");

        try {
            init(Double.parseDouble(part[0]), Double.parseDouble(part[1]),
                Double.parseDouble(part[2]), Integer.parseInt(part[3]));
        } catch (Exception e) {

            System.err.println("Exception on attemping to rebuild Location from String.");
            init(0.0D, 0.0D, 0.0D, 0);
        }
    }

    private static int round(double pos) {
        return (int) Math.floor(pos);
    }

    private void init(double posX, double posY, double posZ, int dimension) {
        this.x = round(posX);
        this.y = round(posY);
        this.z = round(posZ);

        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;

        this.dimension = dimension;
    }

    public void setSpawn(EntityPlayerMP player) {
        player.setSpawnChunk(new ChunkCoordinates(this.x, this.z, this.y), true);
    }

    public String toString() {
        return this.posX + "," + this.posY + "," + this.posZ + "," + this.dimension;
    }


    public boolean equals(Object o) {
        if (o instanceof Location) {

            Location location = (Location) o;
            boolean equal = true;
            equal = (equal && this.posX == location.posX);
            equal = (equal && this.posY == location.posY);
            equal = (equal && this.posZ == location.posZ);
            equal = (equal && this.dimension == location.dimension);
            return equal;
        }
        return false;
    }
}
