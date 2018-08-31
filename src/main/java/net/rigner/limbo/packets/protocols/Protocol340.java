package net.rigner.limbo.packets.protocols;

import net.rigner.limbo.NetworkManager;
import net.rigner.limbo.PlayerConnection;
import net.rigner.limbo.packets.AbstractProtocol;
import net.rigner.limbo.packets.Status;
import net.rigner.limbo.packets.in.PacketInIgnored;
import net.rigner.limbo.packets.in.PacketLoginInStart;
import net.rigner.limbo.packets.in.PacketPlayInKeepAlive339;
import net.rigner.limbo.packets.in.PacketStatusInPing;
import net.rigner.limbo.packets.in.PacketStatusInRequest;
import net.rigner.limbo.packets.out.PacketLoginOutSuccess;
import net.rigner.limbo.packets.out.PacketOutDisconnect;
import net.rigner.limbo.packets.out.PacketPlayOutChunkData110;
import net.rigner.limbo.packets.out.PacketPlayOutJoinGame108;
import net.rigner.limbo.packets.out.PacketPlayOutKeepAlive339;
import net.rigner.limbo.packets.out.PacketPlayOutPlayerPositionAndLook107;
import net.rigner.limbo.packets.out.PacketPlayOutUpdateBlockEntity110;
import net.rigner.limbo.packets.out.PacketStatusOutPong;
import net.rigner.limbo.packets.out.PacketStatusOutResponse;
import net.rigner.limbo.world.Chunk;
import net.rigner.limbo.world.SignTileEntity;

public class Protocol340 extends AbstractProtocol
{
    public Protocol340(NetworkManager networkManager)
    {
        super(networkManager);

        this.registerPacketIn(Status.STATUS, 0x00, PacketStatusInRequest.class);
        this.registerPacketIn(Status.STATUS, 0x01, PacketStatusInPing.class);
        this.registerPacketOut(Status.STATUS, 0x00, PacketStatusOutResponse.class);
        this.registerPacketOut(Status.STATUS, 0x01, PacketStatusOutPong.class);

        this.registerPacketIn(Status.LOGIN, 0x00, PacketLoginInStart.class);
        this.registerPacketOut(Status.LOGIN, 0x00, PacketOutDisconnect.class);
        this.registerPacketOut(Status.LOGIN, 0x02, PacketLoginOutSuccess.class);

        this.registerPacketOut(Status.PLAY, 0x09, PacketPlayOutUpdateBlockEntity110.class);
        this.registerPacketOut(Status.PLAY, 0x1A, PacketOutDisconnect.class);
        this.registerPacketOut(Status.PLAY, 0x1F, PacketPlayOutKeepAlive339.class);
        this.registerPacketOut(Status.PLAY, 0x20, PacketPlayOutChunkData110.class);
        this.registerPacketOut(Status.PLAY, 0x23, PacketPlayOutJoinGame108.class);
        this.registerPacketOut(Status.PLAY, 0x2F, PacketPlayOutPlayerPositionAndLook107.class);

        this.registerPacketIn(Status.PLAY, 0x00, PacketInIgnored.class); // Teleport confirm
        this.registerPacketIn(Status.PLAY, 0x01, PacketInIgnored.class); // Tab complete
        this.registerPacketIn(Status.PLAY, 0x02, PacketInIgnored.class); // Chat message
        this.registerPacketIn(Status.PLAY, 0x03, PacketInIgnored.class); // Client status
        this.registerPacketIn(Status.PLAY, 0x04, PacketInIgnored.class); // Client Settings
        this.registerPacketIn(Status.PLAY, 0x05, PacketInIgnored.class); // Confirm transaction
        this.registerPacketIn(Status.PLAY, 0x06, PacketInIgnored.class); // Enchant item
        this.registerPacketIn(Status.PLAY, 0x07, PacketInIgnored.class); // Click window
        this.registerPacketIn(Status.PLAY, 0x08, PacketInIgnored.class); // Close window
        this.registerPacketIn(Status.PLAY, 0x09, PacketInIgnored.class); // Plugin message
        this.registerPacketIn(Status.PLAY, 0x0A, PacketInIgnored.class); // Use entity
        this.registerPacketIn(Status.PLAY, 0x0B, PacketPlayInKeepAlive339.class);
        this.registerPacketIn(Status.PLAY, 0x0C, PacketInIgnored.class); // Player (ground)
        this.registerPacketIn(Status.PLAY, 0x0D, PacketInIgnored.class); // Player position
        this.registerPacketIn(Status.PLAY, 0x0E, PacketInIgnored.class); // Player position and look
        this.registerPacketIn(Status.PLAY, 0x0F, PacketInIgnored.class); // Player look
        this.registerPacketIn(Status.PLAY, 0x10, PacketInIgnored.class); // Vehicle Move
        this.registerPacketIn(Status.PLAY, 0x11, PacketInIgnored.class); // Steer boat
        this.registerPacketIn(Status.PLAY, 0x12, PacketInIgnored.class); // Craft recipe request
        this.registerPacketIn(Status.PLAY, 0x13, PacketInIgnored.class); // Player abilities
        this.registerPacketIn(Status.PLAY, 0x14, PacketInIgnored.class); // Player digging
        this.registerPacketIn(Status.PLAY, 0x15, PacketInIgnored.class); // Entity action
        this.registerPacketIn(Status.PLAY, 0x16, PacketInIgnored.class); // Steer vehicle
        this.registerPacketIn(Status.PLAY, 0x17, PacketInIgnored.class); // Crafting book data
        this.registerPacketIn(Status.PLAY, 0x18, PacketInIgnored.class); // Resource pack status
        this.registerPacketIn(Status.PLAY, 0x19, PacketInIgnored.class); // Advancement tab
        this.registerPacketIn(Status.PLAY, 0x1A, PacketInIgnored.class); // Held item change
        this.registerPacketIn(Status.PLAY, 0x1B, PacketInIgnored.class); // Creative inventory action
        this.registerPacketIn(Status.PLAY, 0x1C, PacketInIgnored.class); // Update sign
        this.registerPacketIn(Status.PLAY, 0x1D, PacketInIgnored.class); // Animation
        this.registerPacketIn(Status.PLAY, 0x1E, PacketInIgnored.class); // Spectate
        this.registerPacketIn(Status.PLAY, 0x1F, PacketInIgnored.class); // Block placement
        this.registerPacketIn(Status.PLAY, 0x20, PacketInIgnored.class); // Use item
    }

    @Override
    public void sendKeepAlive(PlayerConnection playerConnection, int id)
    {
        this.networkManager.sendPacket(playerConnection, new PacketPlayOutKeepAlive339(id));
    }

    @Override
    public void sendChunk(PlayerConnection playerConnection, Chunk chunk)
    {
        this.networkManager.sendPacket(playerConnection, new PacketPlayOutChunkData110(chunk));
    }

    @Override
    public void sendSign(PlayerConnection playerConnection, SignTileEntity signTileEntity)
    {
        //this.networkManager.sendPacket(playerConnection, new PacketPlayOutUpdateBlockEntity110(signTileEntity, (byte) 9));
    }

    @Override
    public void disconnect(PlayerConnection playerConnection, String message)
    {
        this.networkManager.sendPacket(playerConnection, new PacketOutDisconnect(message));
    }

    @Override
    public void sendJoinGame(PlayerConnection playerConnection, int entityId, byte gameMode, int dimension, byte difficulty, byte maxPlayers, String levelType, boolean debugInfo)
    {
        this.networkManager.sendPacket(playerConnection, new PacketPlayOutJoinGame108(entityId, gameMode, dimension, difficulty, maxPlayers, levelType, debugInfo));
    }

    @Override
    public void sendPosition(PlayerConnection playerConnection, double x, double y, double z, float yaw, float pitch)
    {
        this.networkManager.sendPacket(playerConnection, new PacketPlayOutPlayerPositionAndLook107(x, y, z, yaw, pitch, (byte) 0, 0));
    }

    @Override
    public int[] getVersions()
    {
        return new int[]{ 340 };
    }
}