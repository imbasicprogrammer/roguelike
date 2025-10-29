import greenfoot.*;

/**
 * Kelas Induk ABSTRAK untuk semua musuh.
 * Dia mewarisi 'Character' (animasi, HP, dll.)
 * * Tugas utamanya adalah menangani apa yang terjadi saat musuh mati,
 * yaitu memberi tahu 'Dungeon' (manajer gelombang) dan menghilang.
 */
public abstract class Enemy extends Character
{

    
    protected int xpValue = 10; // Default XP value, bisa di-override anak

    public int getXpValue() {
        return xpValue;
    }
    /**
     * IMPLEMENTASI METHOD WAJIB (dari Character):
     * * Inilah yang terjadi saat HP musuh <= 0.
     * Ini adalah logika yang SAMA untuk SEMUA musuh.
     */
    @Override
    protected void onDeath()
    {
        // 1. Beri tahu Dungeon (World) bahwa 1 musuh telah kalah
        // Ini PENTING untuk Manajer Gelombang (Wave Manager)
        if (getWorld() instanceof Dungeons) {
            ((Dungeons)getWorld()).enemyDefeated(this);
        }
        
        // 2. Hilangkan diri dari dunia
        getWorld().removeObject(this);
    }
}