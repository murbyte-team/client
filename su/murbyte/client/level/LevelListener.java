package su.murbyte.client.level;

public interface LevelListener {
    void tileChanged(int paramInt1, int paramInt2, int paramInt3);

    void lightColumnChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

    void allChanged();
}
