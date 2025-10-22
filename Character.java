import greenfoot.*;

/**
 * Kelas template (Induk) untuk semua karakter yang bisa beranimasi.
 * Kelas ini berisi 'mesin' animasi.
 * Kelas anak (seperti Player atau Enemy) HANYA perlu menyediakan gambar
 * dan logika untuk mengubah status.
 */
public abstract class Character extends Actor
{
    // 1. Variabel 'mesin' (protected agar bisa diakses anak)
    protected GreenfootImage[] idleImages;
    protected GreenfootImage[] walkRightImages;
    protected GreenfootImage[] walkLeftImages;
    protected GreenfootImage[] walkUpImages;   
    protected GreenfootImage[] walkDownImages;
    protected GreenfootImage[] attackRightImages;
    protected GreenfootImage[] attackLeftImages;
    protected GreenfootImage[] attackUpImages;   
    protected GreenfootImage[] attackDownImages;
    
    protected int currentImage = 0;
    protected boolean isAttacking = false;
    protected int animationTimer = 0;
    
    // Bisa di-override oleh anak jika perlu kecepatan beda
    protected int animationDelay = 10; 
    protected int speed = 1;
    
    // Status utama, di-manage oleh anak
    protected String currentState = "idle";
    
    /**
     * Constructor Induk:
     * Memanggil method 'loadImages()' yang akan diisi oleh anak.
     * Lalu, mengatur gambar awal.
     */
    public Character() {
        loadImages(); // Memanggil method yang di-implementasi oleh anak
        
        // Set gambar awal setelah di-load
        if (idleImages != null && idleImages.length > 0) {
            setImage(idleImages[0]);
        }
    }

    /**
     * 'act()' final berarti anak tidak bisa mengubah urutan ini.
     * 1. Anak menentukan status (determineState).
     * 2. Induk memutar animasi (animate).
     */
    public final void act()
    {
        determineState(); // Dijalankan oleh anak (cek input / AI)
        animate();        // Dijalankan oleh induk (mesin animasi)
    }
    
    // --- METHOD WAJIB UNTUK ANAK ---
    
    /**
     * METHOD ABSTRAK:
     * Anak WAJIB mengisi method ini.
     * Gunakan method ini untuk mengisi semua array gambar (idleImages, walkImages, dll.)
     */
    protected abstract void loadImages();
    
    /**
     * METHOD ABSTRAK:
     * Anak WAJIB mengisi method ini.
     * Gunakan method ini untuk cek input keyboard (Player) atau logika AI (Enemy).
     * Method ini harus mengatur 'currentState' dan 'isAttacking'.
     */
    protected abstract void determineState();

    
    // --- MESIN ANIMASI (Milik Induk) ---
    
    /**
     * Method 'animate()' ini 100% modular.
     * Dia hanya peduli pada 'currentState' dan 'isAttacking',
     * tidak peduli siapa yang mengaturnya (Player atau Enemy).
     */
    protected void animate() {
        animationTimer++;
        
        if (animationTimer % animationDelay == 0)
        {
            // 1. Tentukan array mana yang mau dipakai
            GreenfootImage[] currentAnimation;
            
            // --- Blok switch 'currentState' (Sama seperti kode Anda) ---
            if (currentState.equals("walkRight")) {
                currentAnimation = walkRightImages;
            } 
            else if (currentState.equals("walkLeft")) {
                currentAnimation = walkLeftImages;
            }
            else if (currentState.equals("walkUp")) {
                currentAnimation = walkUpImages;
            }
            else if (currentState.equals("walkDown")) {
                currentAnimation = walkDownImages;
            }
            else if (currentState.equals("AttackRight")) {
                currentAnimation = attackRightImages;
            }
            else if (currentState.equals("AttackLeft")) {
                currentAnimation = attackLeftImages;
            }
            else if (currentState.equals("AttackUp")) {
                currentAnimation = attackUpImages;
            } 
            else if (currentState.equals("AttackDown")) {
                currentAnimation = attackDownImages;
            }
            else { // Default-nya adalah idle
                currentAnimation = idleImages;
            }
            
            // Jika array-nya tidak di-load (null), default ke idle
            if (currentAnimation == null) {
                currentAnimation = idleImages;
                if (currentAnimation == null) return; // Tidak ada gambar sama sekali
            }

            // --- Logika "One-Shot" vs "Looping" (Sama seperti kode Anda) ---
            
            if (currentImage >= currentAnimation.length - 1) 
            {
                // JIKA animasi ini adalah animasi ATTACK...
                if (isAttacking) {
                    setImage(currentAnimation[currentImage]); // Tampilkan frame terakhir
                    
                    // Selesai menyerang!
                    isAttacking = false;    // Buka kunci
                    currentState = "idle";  // Kembali ke idle
                    currentImage = 0;       // Reset untuk animasi idle
                }
                // JIKA ini animasi looping biasa (walk/idle)
                else {
                    currentImage = 0; // Loop kembali ke 0
                    setImage(currentAnimation[currentImage]);
                }
            }
            // JIKA ini bukan frame terakhir
            else {
                // Lanjutkan animasi seperti biasa
                currentImage++;
                setImage(currentAnimation[currentImage]);
            }
        }
    }
}