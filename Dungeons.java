import greenfoot.*;
import java.util.List;

/**
 * Dungeon (Dunia) sekarang mengelola beberapa level dan gelombang.
 */
public class Dungeons extends World
{
    // --- Variabel Manajer Level & Gelombang ---
    private int currentLevel = 1;      // Mulai dari level 1
    private int maxWavesPerLevel = 3;  // Awalnya 3 wave per level
    private int currentWave = 0;       // Dimulai dari 0 agar wave 1 yang pertama spawn
    private int enemiesRemaining = 0;
    private int waveTimer = 180;       // Jeda 3 detik

    private String notificationText = ""; // Teks notifikasi saat ini
    private int notificationTimer = 0;
    // (Opsional) Lacak skor
    // private int score = 0;

    /**
     * Constructor untuk kelas Dungeons.
     */
    public Dungeons()
    {
        // 1. Buat dunianya
        super(800, 600, 1);

        // 2. Siapkan Player
        preparePlayer(); // Ganti nama prepare() agar lebih jelas

        // 3. Bangun map untuk level pertama
        buildMap(2);

        // 4. Set Max Waves untuk Level 1
        //setMaxWavesForLevel(currentLevel);
        setMaxWavesForLevel(2);

        // (Jangan panggil spawnWave di sini, biarkan act() memulainya)
    }

    public void showNotification(String text, int durationSeconds) {
        notificationText = text;
        notificationTimer = durationSeconds * 60; // Konversi detik ke frame (asumsi 60fps)

        // Langsung tampilkan teksnya
        showText(notificationText, getWidth() / 2, getHeight() - 50); // Posisi tengah bawah
    }

    /**
     * Metode 'act()' Dunia adalah "Otak" atau "Game Loop" utama.
     * Mengelola alur gelombang dan perpindahan level.
     */
    public void act()
    {
        if (notificationTimer > 0) {
            notificationTimer--; // Kurangi timer
            if (notificationTimer == 0) {
                // Timer habis, hapus teks
                showText("", getWidth() / 2, getHeight() - 50);
                notificationText = "";
            }
        }

        // Hanya cek wave jika ada musuh atau timer berjalan
        if (enemiesRemaining <= 0)
        {
            waveTimer--;
            showText("Gelombang " + (currentWave + 1) + " akan datang...", 400, 30);

            if (waveTimer <= 0)
            {
                currentWave++; // Naikkan nomor wave

                // --- LOGIKA BARU: Cek Pindah Level ---
                if (currentWave > maxWavesPerLevel)
                {
                    // LEVEL SELESAI!
                    currentLevel++;       // Naik ke Level berikutnya
                    currentWave = 1;      // Reset hitungan wave ke 1 untuk level baru
                    buildMap(currentLevel); // Bangun map BARU!
                    setMaxWavesForLevel(currentLevel); // Update max waves

                    // Pindahkan player ke posisi awal
                    // (Ganti 'player' dengan 'PlayableCharacter' jika Bos sudah ganti nama)
                    List<player> players = getObjects(player.class);
                    if (!players.isEmpty()) {
                        players.get(0).setLocation(113, 60); // (Posisi awal dari preparePlayer())
                    }

                    // Tampilkan pesan transisi
                    showText("LEVEL " + currentLevel, 400, 300);
                    Greenfoot.delay(120); // Jeda 2 detik
                    showText("", 400, 300);
                }

                // Panggil spawnWave (sekarang perlu tahu level & wave)
                spawnWave(currentLevel, currentWave);
                waveTimer = 180; // Reset jeda
                showText("", 400, 30); // Hapus teks hitung mundur
            }
        }
    }

    /**
     * Metode ini dipanggil oleh musuh saat mereka mati.
     */
    public void enemyDefeated(Enemy defeatedEnemy) // <-- GANTI DENGAN INI
    {
        enemiesRemaining--; 

        // --- LOGIKA BARU: BERIKAN XP ---
        if (defeatedEnemy != null) {
            int xpGained = defeatedEnemy.getXpValue();

            // Cari player (Gunakan 'player' atau 'PlayableCharacter')
            List<player> players = getObjects(player.class); 
            if (!players.isEmpty()) {
                players.get(0).gainXp(xpGained); // Panggil metode baru di player
            }
        }
        // --- AKHIR LOGIKA BARU ---

        // (Opsional: Tambah skor)
    }

    /**
     * Mengatur jumlah maksimum wave berdasarkan level saat ini.
     */
    private void setMaxWavesForLevel(int level) {
        if (level == 1) {
            maxWavesPerLevel = 3;
        } else if (level == 2) {
            maxWavesPerLevel = 5;
        }
        // Tambahkan else if untuk level selanjutnya
    }

    /**
     * Mengatur JUMLAH dan JENIS musuh berdasarkan level dan wave.
     */
    private void spawnWave(int level, int wave)
    {
        showText("Level: " + level + " - Wave: " + wave + "/" + maxWavesPerLevel, 100, 30);

        if (level == 1) {
            // --- Musuh untuk LEVEL 1 ---
            if (wave == 1) {
                enemiesRemaining = 3;
                spawnRandomEnemies(Vampire.class, 3);
            } else if (wave == 2) {
                enemiesRemaining = 5;
                spawnRandomEnemies(Vampire.class, 5);
            } else if (wave == 3) { // Wave terakhir di Level 1
                enemiesRemaining = 6;
                spawnRandomEnemies(Vampire.class, 6);
                // spawnRandomEnemies(Skeleton.class, 2);
            }

        }
        else if (level == 2) {
            // --- Musuh untuk LEVEL 2 ---
            if (wave == 1) {
                enemiesRemaining = 4; // Contoh: Mulai dengan 4
                spawnRandomEnemies(Vampire.class, 4);
            } else if (wave == 2) {
                enemiesRemaining = 6;
                spawnRandomEnemies(Vampire.class, 6);
                // spawnRandomEnemies(Skeleton.class, 2);
            } else if (wave == 3) {
                enemiesRemaining = 8;
                spawnRandomEnemies(Vampire.class, 8);
                // spawnRandomEnemies(Skeleton.class, 3);
            } else if (wave == 4) {
                enemiesRemaining = 10;
                spawnRandomEnemies(Vampire.class, 10);
                // spawnRandomEnemies(Skeleton.class, 10); // Contoh: Full Skeleton
            } else if (wave == 5) { // Wave terakhir di Level 2
                enemiesRemaining = 1; // Contoh: Boss
                spawnRandomEnemies(Vampire.class, 1);
                // addObject(new BossEnemy(), 400, 300); // Harus buat kelas BossEnemy
            }
        }
        else {
            // Jika Bos mengalahkan level terakhir
            showText("ANDA MENANG!", 400, 300);
            Greenfoot.stop();
        }
    }

    /**
     * Memunculkan musuh di lokasi acak yang aman.
     * (Kode ini sudah benar)
     */
    private <T extends Character> void spawnRandomEnemies(Class<T> enemyType, int count)
    {
        int spawned = 0;
        int worldWidth = getWidth();
        int worldHeight = getHeight();

        // Dapatkan referensi Player (gunakan PlayableCharacter jika sudah ganti nama)
        player player = null;
        if (!getObjects(player.class).isEmpty()) {
            player = getObjects(player.class).get(0);
        }

        while (spawned < count)
        {
            int x = Greenfoot.getRandomNumber(worldWidth);
            int y = Greenfoot.getRandomNumber(worldHeight);

            // Cek Keamanan
            if (!getObjectsAt(x, y, Wall.class).isEmpty()) continue;
            if (player != null && Math.abs(player.getX() - x) < 150 && Math.abs(player.getY() - y) < 150) continue;
            if (!getObjectsAt(x, y, Character.class).isEmpty()) continue;

            // Lokasi Aman
            try {
                Character newEnemy = enemyType.getDeclaredConstructor().newInstance();
                addObject(newEnemy, x, y);
                spawned++;
            }
            catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }

    /**
     * Metode ini HANYA menempatkan Player di awal.
     */
    private void preparePlayer()
    {
        // Bos bisa memilih karakter di sini atau dari menu
        player Knight = new Knight(); // Pastikan nama kelas benar
        addObject(Knight, 113, 60);
    }

    /**
     * Metode ini membangun layout tembok berdasarkan level.
     */
    private void buildMap(int levelNumber)
    {
        // 1. Hapus tembok lama
        removeObjects(getObjects(Wall.class));

        if (levelNumber == 1)
        {
            // --- KODE LAYOUT LEVEL 1 ---
            // (Ini adalah 100+ baris 'addObject(new Wall...)' Bos
            //  yang dipindah dari 'prepare()' lama)
            Wall wall = new Wall();
            addObject(wall,512,11);
            Wall wall2 = new Wall();
            addObject(wall2,512,37); // Disederhanakan dari kode Bos
            Wall wall3 = new Wall();
            addObject(wall3,512,63);
            Wall wall4 = new Wall();
            addObject(wall4,512,89);
            Wall wall5 = new Wall();
            addObject(wall5,512,115);
            Wall wall6 = new Wall();
            addObject(wall6,512,139);
            Wall wall7 = new Wall();
            addObject(wall7,512,163);
            Wall wall8 = new Wall();
            addObject(wall8,512,186);
            Wall wall9 = new Wall();
            addObject(wall9,512,212);
            Wall wall10 = new Wall();
            addObject(wall10,512,238);
            Wall wall11 = new Wall();
            addObject(wall11,511,264);
            Wall wall12 = new Wall();
            addObject(wall12,511,288);
            Wall wall13 = new Wall();
            addObject(wall13,534,288);
            Wall wall14 = new Wall();
            addObject(wall14,556,288);
            Wall wall15 = new Wall();
            addObject(wall15,581,288);
            Wall wall16 = new Wall();
            addObject(wall16,788,288);
            Wall wall17 = new Wall();
            addObject(wall17,762,287);
            Wall wall18 = new Wall();
            addObject(wall18,736,288);
            Wall wall19 = new Wall();
            addObject(wall19,710,287);
            Wall wall20 = new Wall();
            addObject(wall20,784,586); // Disederhanakan
            Wall wall21 = new Wall();
            addObject(wall21,783,560);
            Wall wall22 = new Wall();
            addObject(wall22,783,533);
            Wall wall23 = new Wall();
            addObject(wall23,783,508);
            Wall wall24 = new Wall();
            addObject(wall24,782,481);
            Wall wall25 = new Wall();
            addObject(wall25,781,453);
            Wall wall26 = new Wall();
            addObject(wall26,755,453);
            Wall wall27 = new Wall();
            addObject(wall27,728,452);
            Wall wall28 = new Wall();
            addObject(wall28,702,451);
            Wall wall29 = new Wall();
            addObject(wall29,675,451);
            Wall wall30 = new Wall();
            addObject(wall30,674,478);
            Wall wall31 = new Wall();
            addObject(wall31,674,504);
            Wall wall32 = new Wall();
            addObject(wall32,673,529);
            Wall wall33 = new Wall();
            addObject(wall33,673,556);
            Wall wall34 = new Wall();
            addObject(wall34,672,583);
            Wall wall35 = new Wall();
            addObject(wall35,694,583);
            Wall wall36 = new Wall();
            addObject(wall36,716,582);
            Wall wall37 = new Wall();
            addObject(wall37,739,584);
            Wall wall38 = new Wall();
            addObject(wall38,761,583);
            Wall wall39 = new Wall();
            addObject(wall39,276,583);
            Wall wall40 = new Wall();
            addObject(wall40,276,556);
            Wall wall41 = new Wall();
            addObject(wall41,276,529);
            Wall wall42 = new Wall();
            addObject(wall42,276,502);
            Wall wall43 = new Wall();
            addObject(wall43,276,475);
            Wall wall44 = new Wall();
            addObject(wall44,275,448);
            Wall wall45 = new Wall();
            addObject(wall45,274,421);
            Wall wall46 = new Wall();
            addObject(wall46,249,421);
            Wall wall47 = new Wall();
            addObject(wall47,223,420);
            Wall wall48 = new Wall();
            addObject(wall48,199,421);
            Wall wall49 = new Wall();
            addObject(wall49,175,421);
            Wall wall50 = new Wall();
            addObject(wall50,149,420);
            Wall wall51 = new Wall();
            addObject(wall51,123,421);
            Wall wall52 = new Wall();
            addObject(wall52,122,447);
            Wall wall53 = new Wall();
            addObject(wall53,123,473);
            Wall wall54 = new Wall();
            addObject(wall54,123,499);
            Wall wall55 = new Wall();
            addObject(wall55,146,500);
            Wall wall56 = new Wall();
            addObject(wall56,169,499);
            Wall wall57 = new Wall();
            addObject(wall57,195,11);
            Wall wall58 = new Wall();
            addObject(wall58,220,12);
            Wall wall59 = new Wall();
            addObject(wall59,245,12);
            Wall wall60 = new Wall();
            addObject(wall60,268,12);
            Wall wall61 = new Wall();
            addObject(wall61,291,12);
            Wall wall62 = new Wall();
            addObject(wall62,315,12);
            Wall wall63 = new Wall();
            addObject(wall63,339,12);
            Wall wall64 = new Wall();
            addObject(wall64,362,13);
            Wall wall65 = new Wall();
            addObject(wall65,364,37); // Disederhanakan
            Wall wall66 = new Wall();
            addObject(wall66,363,64);
            Wall wall67 = new Wall();
            addObject(wall67,363,91);
            Wall wall68 = new Wall();
            addObject(wall68,363,118);
            Wall wall69 = new Wall();
            addObject(wall69,337,118);
            Wall wall70 = new Wall();
            addObject(wall70,315,119);
            Wall wall72 = new Wall();
            addObject(wall72,247,111);
            Wall wall73 = new Wall();
            addObject(wall73,222,112);
            Wall wall74 = new Wall();
            addObject(wall74,197,111);
            Wall wall75 = new Wall();
            addObject(wall75,197,87);
            Wall wall76 = new Wall();
            addObject(wall76,196,63);
            Wall wall77 = new Wall();
            addObject(wall77,196,38);
            Wall wall78 = new Wall();
            addObject(wall78,10,234);
            Wall wall79 = new Wall();
            addObject(wall79,34,234);
            Wall wall80 = new Wall();
            addObject(wall80,57,234);
            Wall wall81 = new Wall();
            addObject(wall81,81,233);
            Wall wall82 = new Wall();
            addObject(wall82,105,235);
            Wall wall83 = new Wall();
            addObject(wall83,127,235);
            Wall wall84 = new Wall();
            addObject(wall84,150,235);
            Wall wall85 = new Wall();
            addObject(wall85,173,235);
            Wall wall86 = new Wall();
            addObject(wall86,195,235);
            Wall wall87 = new Wall();
            addObject(wall87,217,235);
            Wall wall88 = new Wall();
            addObject(wall88,239,234);
            Wall wall89 = new Wall();
            addObject(wall89,262,233);
            Wall wall90 = new Wall();
            addObject(wall90,286,233);
            Wall wall91 = new Wall();
            addObject(wall91,284,258);
            Wall wall92 = new Wall();
            addObject(wall92,284,284);
            Wall wall93 = new Wall();
            addObject(wall93,259,285);
            Wall wall94 = new Wall();
            addObject(wall94,234,284);
            Wall wall95 = new Wall();
            addObject(wall95,208,285);
            Wall wall96 = new Wall();
            addObject(wall96,183,286);
            Wall wall97 = new Wall();
            addObject(wall97,159,284);
            Wall wall98 = new Wall();
            addObject(wall98,135,284);
            Wall wall99 = new Wall();
            addObject(wall99,109,284);
            Wall wall100 = new Wall();
            addObject(wall100,86,284);
            Wall wall101 = new Wall();
            addObject(wall101,60,284);
            Wall wall102 = new Wall();
            addObject(wall102,35,283);
            Wall wall103 = new Wall();
            addObject(wall103,13,283);
            Wall wall104 = new Wall();
            addObject(wall104,604,288);
            // --- Akhir Layout Level 1 ---
        }
        else if (levelNumber == 2)
        {
            setBackground("Tile/sand.jpg");
            // --- KODE LAYOUT LEVEL 2 ---
            // Gunakan metode "Save the World" atau ketik manual
            // untuk mendesain layout baru di sini.
            // Contoh: Arena Kotak Sederhana
            int wallSize = 32;
            for (int x = wallSize/2; x < getWidth(); x += wallSize) {
                addObject(new Wall(), x, wallSize/2);
                addObject(new Wall(), x, getHeight() - wallSize/2);
            }
            for (int y = wallSize/2 + wallSize; y < getHeight() - wallSize; y += wallSize) {
                addObject(new Wall(), wallSize/2, y);
                addObject(new Wall(), getWidth() - wallSize/2, y);
            }
            // Tambahkan beberapa rintangan di tengah
            addObject(new Wall(), 200, 300);
            addObject(new Wall(), 232, 300);
            addObject(new Wall(), 600, 300);
            addObject(new Wall(), 568, 300);
            
            
            Wall wall1 = new Wall();
            addObject(wall1,404,299);
            Wall wall2 = new Wall();
            addObject(wall2,405,328);
            Wall wall3 = new Wall();
            addObject(wall3,401,264);
            Wall wall4 = new Wall();
            addObject(wall4,398,227);

        }
        // Tambahkan 'else if (levelNumber == 3)' untuk level selanjutnya
    }

    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */

    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */

    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {

        
    }
}