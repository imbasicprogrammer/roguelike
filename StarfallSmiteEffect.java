import greenfoot.*;
import java.util.List;

/**
 * Efek visual dan damage untuk skill Starfall Smite.
 * Muncul, meledak sekali, lalu hilang.
 */
public class StarfallSmiteEffect extends Actor
{
    // --- Konfigurasi Skill ---
    private int damage = 100;      // Damage ledakan
    private int radius = 150;      // Jangkauan ledakan (dalam piksel)
    private int animationDelay = 4; // Seberapa cepat animasi ledakan berputar

    // --- Mesin Animasi Mini ---
    private GreenfootImage[] explosionImages;
    private int currentImage = 0;
    private int animationTimer = 0;
    private boolean damageApplied = false; // Pastikan damage hanya sekali

    public StarfallSmiteEffect() {
        loadImages();
        if (explosionImages != null && explosionImages[0] != null) {
            setImage(explosionImages[0]);
        }
    }

    /**
     * Isi dengan frame-frame animasi ledakan "Starfall Smite".
     */
    private void loadImages() {
        // CONTOH: Jika animasi ledakan punya 8 frame
        int frameCount = 8; // Ganti sesuai jumlah gambar Anda
        explosionImages = new GreenfootImage[frameCount];
        for (int i = 0; i < frameCount; i++) {
            // GANTI NAMA FILE & PATH INI!
            String filename = "Skills/StarfallSmite/Starfall-Smite" + i + ".png"; 
            try {
                explosionImages[i] = new GreenfootImage(filename);
                // (Opsional: Sesuaikan ukuran gambar ledakan)
                // explosionImages[i].scale(radius * 2, radius * 2); 
            } catch (Exception e) { // Gambar darurat jika file tidak ada
                explosionImages[i] = new GreenfootImage(radius * 2, radius * 2);
                explosionImages[i].setColor(Color.YELLOW);
                explosionImages[i].fillOval(0, 0, radius * 2, radius * 2);
            }
        }
    }

    /**
     * Dipanggil Greenfoot SEKALI saat Aktor ditambahkan ke dunia.
     * Tempat sempurna untuk memberikan damage AoE.
     */
    @Override
    protected void addedToWorld(World world) {
        applyAreaDamage();
    }

    /**
     * Memberikan damage ke semua musuh dalam jangkauan.
     */
    private void applyAreaDamage() {
        if (damageApplied) return; // Pastikan hanya jalan sekali

        // Dapatkan semua musuh dalam radius ledakan
        List<Enemy> targets = getObjectsInRange(radius, Enemy.class); 
        
        System.out.println("Starfall Smite hit " + targets.size() + " enemies."); // Debugging

        for (Enemy target : targets) {
            target.takeDamage(damage); // Beri damage!
        }
        damageApplied = true; // Tandai bahwa damage sudah diberikan
    }

    /**
     * Memainkan animasi ledakan (tidak looping).
     */
    private void animate() {
        if (explosionImages == null || currentImage >= explosionImages.length || explosionImages[currentImage] == null) {
             // Jika ada masalah gambar atau animasi sudah selesai
             if (getWorld() != null) getWorld().removeObject(this); // Hapus diri
             return;
        }

        animationTimer++;
        if (animationTimer % animationDelay == 0) {
            animationTimer = 0;
            currentImage++; // Pindah ke frame berikutnya

            // Cek apakah animasi sudah selesai
            if (currentImage >= explosionImages.length) {
                // Animasi selesai, hapus diri
                if (getWorld() != null) getWorld().removeObject(this);
            } else {
                // Lanjutkan animasi
                if (explosionImages[currentImage] != null) {
                    setImage(explosionImages[currentImage]);
                }
            }
        }
    }

    public void act()
    {
        // "Penjaga" anti NullPointerException
        if (getWorld() == null) { return; }
        
        // Terus putar animasi sampai selesai
        animate(); 
    }
}