import greenfoot.*;
import java.util.List;

/**
 * Boss untuk Level 1. Versi Vampire yang lebih kuat
 * dengan serangan serudukan (Charge Attack).
 */
public class BossVampire extends Enemy
{
    // --- Stats Boss ---
    private int chargeDamage = 30;    // Damage saat menyeruduk
    private int chargeSpeed = 5;      // Kecepatan saat menyeruduk
    private int chargeCooldown = 0;   // Timer cooldown serudukan
    private int maxChargeCooldown = 180; // Cooldown 3 detik
    private int chargeWindup = 0;     // Timer persiapan sebelum seruduk
    private int maxChargeWindup = 30; // Persiapan 0.5 detik
    private boolean isCharging = false; // Status sedang menyeruduk
    private int chargeTargetX, chargeTargetY; // Ke mana dia akan seruduk

    public BossVampire() {
        super(); // Panggil constructor Enemy -> Character

        // --- Atur Stats Khusus Boss ---
        this.speed = 1;             // Kecepatan jalan normal (sama/lebih lambat)
        this.animationDelay = 12;   // Animasi sedikit lebih lambat
        this.maxHealth = 250;       // HP JAUH lebih besar
        this.health = this.maxHealth;
        this.xpValue = 100;
    }

    /**
     * IMPLEMENTASI WAJIB 1: Load Gambar
     * Isi dengan gambar-gambar Boss Vampire Bos.
     * Mungkin perlu gambar "charge_windup" atau "charging".
     */
    @Override
    protected void loadImages() {
        // --- GANTI DENGAN PATH GAMBAR BOSS BOS ---
        String folderPath = "Enemy/BossVampire/"; // Contoh

        // Contoh Load Idle (Harus ada!)
        int idleFrameCount = 4; // Contoh
        idleImages = new GreenfootImage[idleFrameCount];
        for (int i = 0; i < idleFrameCount; i++) {
             // Sesuaikan path ini
            String filename = folderPath + "Idle/idle" + i + ".png";
            try {
                 idleImages[i] = new GreenfootImage(filename);
                 // (Opsional) Perbesar gambar boss
                 // idleImages[i].scale(idleImages[i].getWidth() * 2, idleImages[i].getHeight() * 2);
            } catch (Exception e) { // Gambar darurat jika file tidak ada
                idleImages[i] = new GreenfootImage(64, 64);
                idleImages[i].setColor(Color.MAGENTA);
                idleImages[i].fill();
            }
        }
        
        int walkRightFrameCount = 5; 
        walkRightImages = new GreenfootImage[walkRightFrameCount];
        for (int i = 0; i < walkRightFrameCount; i++) {
            String filename = folderPath + "Walk/" + "walkright" + i + ".png"; 
            walkRightImages[i] = new GreenfootImage(filename);
        }
        
        int walkLeftFrameCount = 5; 
        walkLeftImages = new GreenfootImage[walkLeftFrameCount];
        for (int i = 0; i < walkLeftFrameCount; i++) {
            String filename = folderPath + "Walk/" + "walkleft" + i + ".png"; 
            walkLeftImages[i] = new GreenfootImage(filename);
        }
        
        int walkUpFrameCount = 5;
        walkUpImages = new GreenfootImage[walkUpFrameCount];
        for (int i = 0; i < walkUpFrameCount; i++) {
            String filename = folderPath + "Walk/" + "walkup" + i + ".png";
            walkUpImages[i] = new GreenfootImage(filename);
        }

        int walkDownFrameCount = 5;
        walkDownImages = new GreenfootImage[walkDownFrameCount];
        for (int i = 0; i < walkDownFrameCount; i++) {
            String filename = folderPath + "Walk/" + "walkdown" + i + ".png";
            walkDownImages[i] = new GreenfootImage(filename);
        }
        
        int attackRightFrameCount = 5; 
        attackRightImages = new GreenfootImage[attackRightFrameCount];
        for (int i = 0; i < attackRightFrameCount; i++) {
            String filename = folderPath + "Attack/" + "attackright" + i + ".png"; 
            attackRightImages[i] = new GreenfootImage(filename);
        }
        
        int attackLeftFrameCount = 5; 
        attackLeftImages = new GreenfootImage[attackLeftFrameCount];
        for (int i = 0; i < attackLeftFrameCount; i++) {
            String filename = folderPath + "Attack/" + "attackleft" + i + ".png"; 
            attackLeftImages[i] = new GreenfootImage(filename);
        }
        
        int attackUpFrameCount = 5;
        attackUpImages = new GreenfootImage[attackUpFrameCount];
        for (int i = 0; i < attackUpFrameCount; i++) {
            String filename = folderPath + "Attack/" + "attackup" + i + ".png";
            attackUpImages[i] = new GreenfootImage(filename);
        }

       int attackDownFrameCount = 5;
        attackDownImages = new GreenfootImage[attackDownFrameCount];
        for (int i = 0; i < attackDownFrameCount; i++) {
            String filename = folderPath + "Attack/" + "VampireAttack" + i + ".png";
            attackDownImages[i] = new GreenfootImage(filename);
        } 
        
        int hurtFrameCount = 4; // Contoh
        hurtImages = new GreenfootImage[hurtFrameCount];
        for (int i = 0; i < hurtFrameCount; i++) {
            String filename = folderPath + "Hurt/" + "VampireHurt" + i + ".png";
            hurtImages[i] = new GreenfootImage(filename);
        }
        
        int deathFrameCount = 11; // Contoh
        deathImages = new GreenfootImage[deathFrameCount];
        for (int i = 0; i < deathFrameCount; i++) {
            String filename = folderPath + "Death/" + "VampireDeath" + i + ".png";
            deathImages[i] = new GreenfootImage(filename);
        }
        // (Pastikan Bos setidaknya mengisi 'idleImages' agar tidak error)
        // Jika Bos belum punya gambar, isi 'idleImages' saja untuk tes:
        if (idleImages == null) {
            idleImages = new GreenfootImage[1];
            idleImages[0] = new GreenfootImage(32, 32); // Gambar kotak 32x32
            idleImages[0].setColor(Color.RED);
            idleImages[0].fill();
        }
        // --- Load Walk, Attack, Hurt, Death (Sama seperti Vampire biasa) ---
        // ...(Salin kode load walk, attack, hurt, death dari Vampire.java,
        //    tapi arahkan path ke folder BossVampire)...
        // --- Pastikan gambar H U R T dan D E A T H ada ---
    }

    /**
     * IMPLEMENTASI WAJIB 2: AI Boss (Otak)
     */
    @Override
    protected void determineState() {
        // 0. Logika Cooldown & State Khusus
        if (chargeCooldown > 0) chargeCooldown--;
        if (isCharging) {
            handleCharging(); // Logika saat sedang menyeruduk
            return; // Hentikan AI normal jika sedang seruduk
        }
        if (chargeWindup > 0) {
            handleChargeWindup(); // Logika saat persiapan seruduk
            return; // Hentikan AI normal jika sedang persiapan
        }

        // Jika sedang menyerang (animasi melee), terluka, atau mati, jangan lakukan AI
        if (isAttacking || isHurt || isDead) {
            return;
        }

        // 1. Temukan Player
        List<player> players = getWorld().getObjects(player.class); // Ganti ke PlayableCharacter jika perlu
        if (players.isEmpty()) {
            currentState = "idle";
            return;
        }
        player player = players.get(0);

        // 2. Inisialisasi
        String newState = "idle";
        int dx = 0;
        int dy = 0;
        int distanceToPlayer = (int) Math.hypot(player.getX() - getX(), player.getY() - getY());

        // 3. LOGIKA AI BOSS:
        // Prioritas 1: Jika bisa seruduk & Player dalam jarak menengah... SERUDUK!
        if (chargeCooldown == 0 && distanceToPlayer > 50 && distanceToPlayer < 250)
        {
            startChargeWindup(player); // Mulai persiapan seruduk
            return; // Hentikan AI normal
        }
        // Prioritas 2: Jika Player sangat dekat... PUKUL BIASA!
        else if (distanceToPlayer <= 50)
        {
            // (Logika Pukul Biasa - Sama seperti Vampire)
            isAttacking = true;
            player.takeDamage(15); // Damage pukul biasa (lebih kecil dari seruduk)

            int deltaX = player.getX() - getX();
            int deltaY = player.getY() - getY();
            if (Math.abs(deltaX) > Math.abs(deltaY)) {
                newState = (deltaX > 0) ? "AttackRight" : "AttackLeft";
            } else {
                newState = (deltaY > 0) ? "AttackDown" : "AttackUp";
            }
        }
        // Prioritas 3: Jika Player jauh... KEJAR BIASA!
        else
        {
            // (Logika Kejar Biasa - Sama seperti Vampire)
             int deltaX = player.getX() - getX();
             int deltaY = player.getY() - getY();
             if (Math.abs(deltaX) > 5 || Math.abs(deltaY) > 5) { // Hanya gerak jika > 5px
                 if (Math.abs(deltaX) > Math.abs(deltaY)) {
                     dx = (deltaX > 0) ? speed : -speed;
                     newState = (deltaX > 0) ? "walkRight" : "walkLeft";
                 } else {
                     dy = (deltaY > 0) ? speed : -speed;
                     newState = (deltaY > 0) ? "walkDown" : "walkUp";
                 }
                 moveWithCollision(dx, dy); // Gunakan gerak anti-tembok
             } else {
                 newState = "idle"; // Jika sudah cukup dekat tapi belum bisa pukul/seruduk
             }
        }

        // 4. Reset Animasi
        if (!currentState.equals(newState)) {
            currentState = newState;
            currentImage = 0;
            animationTimer = 0;
        }
        if (isAttacking) { // Reset untuk animasi attack biasa
            currentImage = 0;
            animationTimer = 0;
        }
    }

    /** Metode untuk memulai persiapan seruduk */
    private void startChargeWindup(player targetPlayer) {
        chargeWindup = maxChargeWindup; // Mulai timer persiapan
        isCharging = false;             // Belum menyeruduk
        // Simpan posisi target SAAT INI
        chargeTargetX = targetPlayer.getX();
        chargeTargetY = targetPlayer.getY();
        // (Opsional) Ganti gambar/animasi ke "windup"
        // currentState = "chargeWindup";
        // currentImage = 0;
    }

    /** Logika saat dalam persiapan seruduk (diam di tempat) */
    private void handleChargeWindup() {
        chargeWindup--; // Kurangi timer
        // (Tetap mainkan animasi windup jika ada)

        if (chargeWindup <= 0) {
            // Waktu persiapan habis, MULAI SERUDUK!
            isCharging = true;
            // (Opsional) Ganti gambar/animasi ke "charging"
            // currentState = "charging";
            // currentImage = 0;
            turnTowards(chargeTargetX, chargeTargetY); // Hadap ke target awal
        }
    }

    /** Logika saat sedang menyeruduk */
    private void handleCharging() {
        // Bergerak lurus dengan kecepatan tinggi
        move(chargeSpeed);

        // Cek tabrakan dengan Player
        player hitPlayer = (player) getOneIntersectingObject(player.class); // Ganti ke PlayableCharacter?
        if (hitPlayer != null) {
            hitPlayer.takeDamage(chargeDamage); // Beri damage besar
            stopCharge(); // Berhenti seruduk setelah kena
            return;
        }

        // Cek tabrakan dengan Tembok
        if (isTouching(Wall.class) || isAtEdge()) {
            stopCharge(); // Berhenti seruduk jika nabrak tembok/pinggir
            // (Opsional: Tambah efek "stun" sebentar setelah nabrak)
            return;
        }

        // Cek apakah sudah melewati target (untuk mencegah overshoot jauh)
        // Ini agak rumit, bisa diabaikan jika tidak perlu
        double distanceToTarget = Math.hypot(chargeTargetX - getX(), chargeTargetY - getY());
        if (distanceToTarget < chargeSpeed) { // Jika sudah sangat dekat/melewati target awal
             stopCharge();
        }
    }

    /** Metode untuk menghentikan serudukan & memulai cooldown */
    private void stopCharge() {
        isCharging = false;
        chargeCooldown = maxChargeCooldown; // Mulai cooldown
        currentState = "idle"; // Kembali ke idle
        currentImage = 0;
    }

    // TIDAK PERLU onDeath() di sini, sudah diwarisi dari Enemy.java
}