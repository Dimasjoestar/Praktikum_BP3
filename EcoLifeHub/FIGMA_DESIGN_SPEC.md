# EcoLifeHub - Figma Design Specification

## Project Information
- **Channel ID**: e54fh7sp
- **App Name**: EcoLifeHub
- **Tagline**: Hidup Hijau, Bumi Sehat
- **Device Spec**: Mobile (375x812 px)

---

## Color Palette

### Primary Colors
- **Primary**: #2E7D32 (Emerald Green)
- **Primary Dark**: #1B5E20 (Dark Green)
- **Primary Light**: #81C784 (Light Green)
- **Primary Variant**: #388E3C

### Secondary Colors
- **Secondary**: #00897B (Teal)
- **Secondary Dark**: #00695C
- **Secondary Light**: #4DB6AC

### Accent Colors
- **Accent**: #C6FF00 (Lime)
- **Accent Dark**: #9E9D24

### Background Colors
- **Background**: #F1F8E9 (Very Light Green)
- **Background Dark**: #E8F5E9
- **Surface**: #FFFFFF (White)
- **Card Background**: #FFFFFF

### Text Colors
- **Text Primary**: #1B5E20
- **Text Secondary**: #558B2F
- **Text on Primary**: #FFFFFF
- **Text Hint**: #A5D6A7
- **Disabled**: #C8E6C9

### Status Colors
- **Success**: #4CAF50
- **Warning**: #FFC107
- **Error**: #F44336
- **Info**: #2196F3

---

## Typography

### Text Sizes
- **Display**: 32sp (Splash screen title)
- **Headline**: 24sp (Page titles)
- **Title**: 20sp (Section titles)
- **Subtitle**: 16sp (Subtitles)
- **Body**: 14sp (Body text)
- **Caption**: 12sp (Small text)

### Font Family
- **Primary Font**: Sans Serif
- **Weight**: 
  - Regular: 400
  - Medium: 500
  - Bold: 700

---

## Spacing System

- **XS**: 4dp
- **SM**: 8dp
- **MD**: 16dp
- **LG**: 24dp
- **XL**: 32dp
- **XXL**: 48dp

---

## Corner Radius

- **SM**: 8dp
- **MD**: 12dp
- **LG**: 16dp
- **XL**: 24dp
- **Circle**: 50dp

---

## Components

### Buttons
- **Height**: 48dp (MD) / 56dp (LG)
- **Background**: Primary color with rounded corners (MD)
- **Text Color**: White
- **Style**: Filled (Primary), Outlined (Secondary)

### Cards
- **Min Height**: 120dp
- **Image Height**: 150dp
- **Corner Radius**: 16dp
- **Elevation**: 4dp (MD shadow)
- **Background**: White

### Input Fields
- **Height**: 48-56dp
- **Corner Radius**: 12dp
- **Border**: Primary color (1-2dp)
- **Background**: White or Light background
- **Hint Color**: Primary light

### Icons
- **Icon Sizes**:
  - SM: 24dp
  - MD: 32dp
  - LG: 48dp
  - XL: 64dp
  - XXL: 96dp
- **Color**: Primary Dark or White (on gradient)

---

## Screen Designs

### 1. SPLASH SCREEN (375x812)
**Location**: Top-left at (0, 0)

**Background**: 
- Gradient: #2E7D32 → #388E3C → #43A047
- Direction: Top to Bottom

**Elements**:
1. **Logo** (150x150 dp)
   - Centered horizontally
   - Position: ~150dp from top
   - Style: EcoLifeHub icon/logo (green with leaf)
   
2. **App Name** (Text)
   - Text: "EcoLifeHub"
   - Font Size: 32sp
   - Font Weight: Bold
   - Color: #FFFFFF
   - Margin Top: 24dp from logo
   - Centered horizontally

3. **Tagline** (Text)
   - Text: "Hidup Hijau, Bumi Sehat"
   - Font Size: 16sp
   - Font Weight: Regular
   - Color: #81C784
   - Margin Top: 8dp from app name
   - Centered horizontally

**Bottom Area**:
- Loading indicator or progress bar (optional)
- Color: #81C784

---

### 2. LOGIN SCREEN (375x812)
**Location**: Top-left at (400, 0)

**Background**: #F1F8E9

**Header Section** (Full Width):
1. **Logo** (100x100 dp)
   - Centered horizontally
   - Margin Top: 48dp

2. **App Name** (Text)
   - Text: "EcoLifeHub"
   - Font Size: 24sp
   - Font Weight: Bold
   - Color: #1B5E20
   - Margin Top: 16dp

3. **Welcome Text** (Text)
   - Text: "Selamat Kembali!"
   - Font Size: 20sp
   - Font Weight: Bold
   - Color: #1B5E20
   - Margin Top: 24dp

4. **Subtitle** (Text)
   - Text: "Masuk untuk melanjutkan"
   - Font Size: 14sp
   - Font Weight: Regular
   - Color: #558B2F
   - Margin Top: 4dp

**Form Section** (Starting ~200dp from top):
1. **Email Input**
   - Label: "Email"
   - Placeholder: "nama@email.com"
   - Height: 56dp
   - Corner Radius: 12dp
   - Border: 1dp #2E7D32
   - Background: #FFFFFF
   - Margin: 16dp horizontal
   - Margin Bottom: 16dp

2. **Password Input**
   - Label: "Password"
   - Placeholder: "••••••••"
   - Height: 56dp
   - Corner Radius: 12dp
   - Border: 1dp #2E7D32
   - Background: #FFFFFF
   - Margin: 16dp horizontal
   - Margin Bottom: 24dp
   - Show/Hide icon on right

3. **Login Button**
   - Text: "Masuk"
   - Height: 56dp
   - Width: Full - 32dp margin
   - Background: #2E7D32
   - Text Color: #FFFFFF
   - Font Size: 16sp
   - Font Weight: Bold
   - Corner Radius: 12dp
   - Margin: 16dp horizontal

**Bottom Section**:
1. **Register Link**
   - Text: "Belum punya akun? Daftar di sini"
   - Font Size: 14sp
   - Color: #2E7D32 (linked text)
   - Centered
   - Margin Top: 24dp

---

### 3. REGISTER SCREEN (375x812)
**Location**: Top-left at (800, 0)

**Background**: #F1F8E9

**Header Section** (Similar to Login):
1. **Logo** (100x100 dp)
   - Centered horizontally
   - Margin Top: 32dp

2. **App Name** (Text)
   - Text: "EcoLifeHub"
   - Font Size: 24sp
   - Font Weight: Bold
   - Color: #1B5E20

3. **Create Account Text** (Text)
   - Text: "Buat Akun"
   - Font Size: 20sp
   - Font Weight: Bold
   - Color: #1B5E20
   - Margin Top: 24dp

4. **Subtitle** (Text)
   - Text: "Bergabunglah dengan komunitas hijau kami"
   - Font Size: 14sp
   - Color: #558B2F
   - Margin Top: 4dp

**Form Section** (Starting ~160dp from top):
1. **Name Input**
   - Label: "Nama Lengkap"
   - Height: 56dp
   - Margin: 16dp horizontal
   - Margin Bottom: 12dp
   - Same styling as login inputs

2. **Email Input**
   - Label: "Email"
   - Height: 56dp
   - Margin: 16dp horizontal
   - Margin Bottom: 12dp

3. **Username Input**
   - Label: "Username"
   - Height: 56dp
   - Margin: 16dp horizontal
   - Margin Bottom: 12dp

4. **Password Input**
   - Label: "Password"
   - Height: 56dp
   - Margin: 16dp horizontal
   - Margin Bottom: 12dp

5. **Confirm Password Input**
   - Label: "Konfirmasi Password"
   - Height: 56dp
   - Margin: 16dp horizontal
   - Margin Bottom: 24dp

6. **Register Button**
   - Text: "Daftar"
   - Height: 56dp
   - Width: Full - 32dp margin
   - Background: #2E7D32
   - Text Color: #FFFFFF
   - Corner Radius: 12dp
   - Margin Bottom: 16dp

**Bottom Section**:
- Text: "Sudah punya akun? Masuk di sini"
- Font Size: 14sp
- Color: #2E7D32
- Centered

---

### 4. HOME SCREEN (375x812)
**Location**: Top-left at (1200, 0)

**Header Section** (Full Width, ~100dp):
- **Background**: Gradient #2E7D32 → #388E3C
- **Top Row** (Logo + App Name):
  - Logo: 48x48 dp
  - App Name: "EcoLifeHub"
  - Font Size: 20sp
  - Font Weight: Bold
  - Color: #FFFFFF

- **Welcome Section** (Below logo):
  - Text: "Selamat Datang!"
  - Font Size: 24sp
  - Font Weight: Bold
  - Color: #FFFFFF
  
  - Subtitle: "Jelajahi menu kami"
  - Font Size: 14sp
  - Color: #81C784

**Menu Grid** (4 columns, 2 rows - RecyclerView):
Starting at ~120dp from top

Each menu item card:
- **Dimensions**: ~80x120 dp
- **Corner Radius**: 16dp
- **Background**: #FFFFFF
- **Elevation**: 4dp shadow
- **Spacing**: 12dp between items
- **Margin**: 16dp on sides

**Menu Items**:
1. **Kategori Limbah** (Waste Category)
   - Icon: ic_waste (64x64)
   - Label: "Kategori Limbah"
   - Font Size: 12sp
   - Color: #1B5E20
   - Icon Color: #2E7D32

2. **Green Tips**
   - Icon: ic_tips (64x64)
   - Label: "Green Tips"
   - Font Size: 12sp
   - Color: #1B5E20
   - Icon Color: #2E7D32

3. **Eco Challenge**
   - Icon: ic_challenge (64x64)
   - Label: "Eco Challenge"
   - Font Size: 12sp
   - Color: #1B5E20
   - Icon Color: #2E7D32

4. **Daily Habit**
   - Icon: ic_habit (64x64)
   - Label: "Daily Habit"
   - Font Size: 12sp
   - Color: #1B5E20
   - Icon Color: #2E7D32

5. **About**
   - Icon: ic_about (64x64)
   - Label: "Tentang"
   - Font Size: 12sp
   - Color: #1B5E20
   - Icon Color: #2E7D32

6. **Settings**
   - Icon: ic_settings (64x64)
   - Label: "Pengaturan"
   - Font Size: 12sp
   - Color: #1B5E20
   - Icon Color: #2E7D32

---

### 5. WASTE CATEGORY SCREEN (375x812)
**Location**: Top-left at (1600, 0)

**Header Section** (Full Width, ~90dp):
- **Background**: Gradient #2E7D32 → #388E3C
- **Top Navigation Row**:
  - Back Arrow Icon: 48x48 dp (Left-aligned)
  - Title: "Kategori Limbah"
  - Font Size: 20sp
  - Font Weight: Bold
  - Color: #FFFFFF
  - Padding: 16dp

- **Subtitle**: "Pilih kategori limbah untuk mempelajari lebih lanjut"
  - Font Size: 14sp
  - Color: #81C784
  - Padding: 0 16dp 16dp 16dp

**Category List** (RecyclerView - Vertical):
Starting at ~110dp

Each category card:
- **Height**: 140dp
- **Width**: Full - 32dp margin
- **Corner Radius**: 16dp
- **Background**: #FFFFFF
- **Elevation**: 4dp
- **Margin**: 16dp horizontal, 12dp vertical
- **Padding**: 16dp

**Card Content**:
1. **Icon Section** (Left):
   - Circular background: 80x80 dp
   - Background Color: #F1F8E9
   - Icon: 64x64 dp
   - Icon Color: #2E7D32

2. **Text Section** (Right):
   - Title: Category name
   - Font Size: 16sp
   - Font Weight: Bold
   - Color: #1B5E20
   
   - Description: "Tap untuk selengkapnya"
   - Font Size: 12sp
   - Color: #558B2F
   
   - Arrow Icon (Right): ic_arrow_right (24x24)
   - Color: #2E7D32

**Categories**:
1. **Plastik** (Plastic)
   - Icon: ic_plastic (orange-tinted green)
   
2. **Kertas** (Paper)
   - Icon: ic_paper (brown-tinted green)
   
3. **Elektronik** (Electronic)
   - Icon: ic_electronic (gray-tinted)
   
4. **Organik** (Organic)
   - Icon: ic_organic (dark green)
   
5. **B3** (Hazardous)
   - Icon: ic_b3 (red-tinted)

---

### 6. WASTE DETAIL SCREEN (375x812)
**Location**: Top-left at (2000, 0)

**Header Section** (Full Width, ~120dp):
- **Background**: Gradient #2E7D32 → #388E3C
- **Top Navigation**:
  - Back Arrow: 48x48 dp
  - Title: Waste name (e.g., "Limbah Plastik")
  - Font Size: 20sp
  - Font Weight: Bold
  - Color: #FFFFFF

- **Icon Section** (Center):
  - Circle background: 96x96 dp
  - Background color: rgba(255, 255, 255, 0.2)
  - Icon: 64x64 dp
  - Icon Color: #1B5E20
  - Margin Top: 16dp

**Content Section** (Scrollable):
Starting at ~140dp

1. **Description Card**:
   - Width: Full - 32dp
   - Margin: 16dp
   - Background: #FFFFFF
   - Padding: 16dp
   - Corner Radius: 12dp
   - Elevation: 2dp
   
   - Title: "Deskripsi"
   - Font Size: 16sp
   - Font Weight: Bold
   - Color: #1B5E20
   - Margin Bottom: 8dp
   
   - Content text: Description of waste
   - Font Size: 14sp
   - Color: #558B2F
   - Line Height: 1.5

2. **Tips Card**:
   - Width: Full - 32dp
   - Margin: 16dp
   - Background: #FFFFFF
   - Padding: 16dp
   - Corner Radius: 12dp
   - Elevation: 2dp
   
   - Title: "Tips Penanganan"
   - Font Size: 16sp
   - Font Weight: Bold
   - Color: #1B5E20
   - Margin Bottom: 12dp
   
   - List items (Bullet points):
     - Font Size: 14sp
     - Color: #558B2F
     - Margin Bottom: 8dp

3. **Impact Card**:
   - Width: Full - 32dp
   - Margin: 16dp
   - Background: #FFFBEA (light yellow)
   - Padding: 16dp
   - Corner Radius: 12dp
   - Border: 1dp #FFC107
   
   - Title: "Dampak Lingkungan"
   - Font Size: 16sp
   - Font Weight: Bold
   - Color: #F57F17
   - Margin Bottom: 8dp
   
   - Impact text
   - Font Size: 14sp
   - Color: #E65100

---

### 7. ECO CHALLENGE SCREEN (375x812)
**Location**: Top-left at (2400, 0)

**Header Section** (Full Width, ~100dp):
- **Background**: Gradient #2E7D32 → #388E3C
- **Top Navigation**:
  - Back Arrow: 48x48 dp
  - Title: "Eco Challenge"
  - Font Size: 20sp
  - Font Weight: Bold
  - Color: #FFFFFF

- **Subtitle**: "Pilih tantangan dan jadilah pahlawan lingkungan!"
  - Font Size: 14sp
  - Color: #81C784
  - Padding: 0 16dp 16dp 16dp

**Challenge List** (RecyclerView - Vertical):
Starting at ~120dp

Each challenge card:
- **Height**: 180dp
- **Width**: Full - 32dp
- **Corner Radius**: 16dp
- **Background**: #FFFFFF
- **Elevation**: 4dp
- **Margin**: 16dp horizontal, 12dp vertical
- **Padding**: 16dp

**Card Content**:
1. **Top Section**:
   - Title: Challenge name
   - Font Size: 16sp
   - Font Weight: Bold
   - Color: #1B5E20
   
   - Description: Challenge description
   - Font Size: 13sp
   - Color: #558B2F
   - Margin Top: 4dp

2. **Middle Section** (Progress bar - Optional):
   - Progress: Xx days remaining
   - Font Size: 12sp
   - Color: #2E7D32
   - Margin Top: 12dp

3. **Bottom Section**:
   - Participants: "Xx orang telah bergabung"
   - Font Size: 12sp
   - Color: #A5D6A7
   - Icon: ic_challenge (16x16)
   
   - Arrow Icon: (Right side)
   - Color: #2E7D32

**Example Challenges**:
- "Kurangi Penggunaan Plastik"
- "Mulai Composting"
- "Hemat Energi"
- "Tanam Pohon"

---

### 8. CHALLENGE DETAIL SCREEN (375x812)
**Location**: Top-left at (2800, 0)

**Header Section** (Full Width, ~120dp):
- **Background**: Gradient #2E7D32 → #388E3C
- **Top Navigation**:
  - Back Arrow: 48x48 dp
  - Title: Challenge name
  - Font Size: 20sp
  - Font Weight: Bold
  - Color: #FFFFFF

- **Icon Section**:
  - Circle: 96x96 dp
  - Background: rgba(255, 255, 255, 0.2)
  - Icon: ic_challenge (64x64)
  - Color: #FFFFFF
  - Margin Top: 16dp

**Content Section** (Scrollable):
Starting at ~150dp

1. **Description Card**:
   - Title: "Tantangan"
   - Content: Full description
   - Same styling as Waste Detail

2. **Objective Card**:
   - Title: "Tujuan"
   - Content: Challenge objectives
   - Bullet points

3. **Progress Card**:
   - Background: #F1F8E9
   - Title: "Progres Anda"
   - Progress bar
   - Xx% completed
   - Days remaining counter
   - Button: "Tandai Selesai" (Green)
   - Button height: 48dp
   - Width: Full - 32dp

4. **Rewards Card**:
   - Background: #FFFBEA
   - Border: 1dp #FFC107
   - Title: "Hadiah"
   - Content: Reward information
   - Icon: ic_check (32x32)
   - Color: #4CAF50

---

### 9. GREEN TIPS SCREEN (375x812)
**Location**: Top-left at (3200, 0)

**Header Section** (Same as Eco Challenge):
- Gradient background
- Back arrow + title
- Subtitle: "Tips praktis untuk gaya hidup ramah lingkungan"

**Tips List** (RecyclerView - Vertical):
Starting at ~120dp

Each tip card:
- **Height**: 160dp
- **Width**: Full - 32dp
- **Corner Radius**: 16dp
- **Background**: #FFFFFF
- **Elevation**: 4dp
- **Margin**: 16dp horizontal, 12dp vertical
- **Padding**: 16dp

**Card Content**:
1. **Top Section**:
   - Title: Tip title
   - Font Size: 16sp
   - Font Weight: Bold
   - Color: #1B5E20

2. **Middle Section**:
   - Description: Short tip description (2-3 lines)
   - Font Size: 13sp
   - Color: #558B2F
   - Margin Top: 8dp

3. **Bottom Section**:
   - Category badge:
     - Background: #E8F5E9
     - Text: Category (e.g., "Hemat Energi")
     - Font Size: 12sp
     - Color: #2E7D32
     - Padding: 4dp 8dp
     - Corner Radius: 4dp
     - Margin Top: 12dp
   
   - Arrow Icon: (Right side)

**Example Tips**:
- "Gunakan tas belanja yang dapat digunakan kembali"
- "Matikan lampu saat tidak digunakan"
- "Mulai menanam tumbuhan di rumah"
- "Reduce, Reuse, Recycle"

---

### 10. DAILY HABIT SCREEN (375x812)
**Location**: Top-left at (3600, 0)

**Header Section** (Full Width, ~100dp):
- **Background**: Gradient #2E7D32 → #388E3C
- **Top Navigation**:
  - Back Arrow: 48x48 dp
  - Title: "Daily Habit"
  - Font Size: 20sp
  - Font Weight: Bold
  - Color: #FFFFFF

- **Subtitle**: "Daftar aktivitas harian Anda"
  - Font Size: 14sp
  - Color: #81C784

**Progress Card** (Starting at ~120dp):
- Width: Full - 32dp
- Margin: 16dp
- Background: #FFFFFF
- Padding: 16dp
- Corner Radius: 16dp
- Elevation: 4dp

**Card Content**:
1. **Title**: "Progres Hari Ini"
   - Font Size: 16sp
   - Font Weight: Bold
   - Color: #1B5E20

2. **Progress Bar**:
   - Full width of card
   - Height: 8dp
   - Background: #E8F5E9
   - Filled: #2E7D32
   - Corner Radius: 4dp
   - Margin Top: 12dp
   - Percentage text below: "3/5 selesai"
   - Font Size: 14sp
   - Color: #558B2F

**Habits List** (RecyclerView - Vertical):
Starting at ~220dp

Each habit item:
- **Height**: 80dp
- **Width**: Full - 32dp
- **Margin**: 16dp horizontal, 8dp vertical
- **Padding**: 12dp
- **Background**: #FFFFFF
- **Border**: 1dp #E0E0E0
- **Corner Radius**: 12dp
- **Elevation**: 2dp

**Item Content**:
1. **Left Side** (Checkbox):
   - Checkbox: 24x24 dp
   - Unchecked: Border #2E7D32
   - Checked: Filled #2E7D32 with ic_check
   - Margin Right: 12dp

2. **Middle Section**:
   - Title: Habit name
   - Font Size: 14sp
   - Font Weight: Bold
   - Color: #1B5E20
   
   - Time: "10:00 - 10:30 AM"
   - Font Size: 12sp
   - Color: #A5D6A7
   - Margin Top: 4dp

3. **Right Side** (Status):
   - Status icon or badge
   - Optional: Streak count

**Example Habits**:
- "Minum air putih"
- "Jalan kaki ke kantor"
- "Cuci tangan dengan hemat air"
- "Makan makanan sehat"

---

### 11. ABOUT SCREEN (375x812)
**Location**: Top-left at (4000, 0)

**Header Section** (Full Width, ~80dp):
- **Background**: Gradient #2E7D32 → #388E3C
- **Top Navigation**:
  - Back Arrow: 48x48 dp
  - Title: "Tentang"
  - Font Size: 20sp
  - Font Weight: Bold
  - Color: #FFFFFF

**Content Section** (Scrollable):
Starting at ~90dp

1. **Logo Section** (Centered):
   - Logo: 120x120 dp
   - Margin Top: 32dp
   - Margin Bottom: 16dp

2. **App Info Card**:
   - Width: Full - 32dp
   - Margin: 16dp
   - Background: #FFFFFF
   - Padding: 16dp
   - Corner Radius: 12dp
   - Elevation: 2dp
   - Centered text
   
   - Title: "EcoLifeHub"
   - Font Size: 20sp
   - Font Weight: Bold
   - Color: #1B5E20
   
   - Version: "Version 1.0.0"
   - Font Size: 12sp
   - Color: #A5D6A7
   - Margin Top: 8dp

3. **Description Card**:
   - Width: Full - 32dp
   - Margin: 16dp
   - Background: #FFFFFF
   - Padding: 16dp
   - Corner Radius: 12dp
   - Elevation: 2dp
   
   - Title: "Tentang Aplikasi"
   - Font Size: 16sp
   - Font Weight: Bold
   - Color: #1B5E20
   - Margin Bottom: 12dp
   
   - Description: "EcoLifeHub adalah aplikasi mobile yang dirancang untuk meningkatkan kesadaran lingkungan dan mendorong masyarakat untuk hidup lebih berkelanjutan..."
   - Font Size: 13sp
   - Color: #558B2F
   - Justified text
   - Margin Bottom: 12dp

4. **Features Card**:
   - Width: Full - 32dp
   - Margin: 16dp
   - Background: #F1F8E9
   - Padding: 16dp
   - Corner Radius: 12dp
   
   - Title: "Fitur Utama"
   - Font Size: 16sp
   - Font Weight: Bold
   - Color: #1B5E20
   - Margin Bottom: 12dp
   
   - Features list (Bullet points):
     - "Pelajari berbagai jenis limbah"
     - "Ikuti tantangan lingkungan"
     - "Dapatkan tips praktis hijau"
     - "Tracking kebiasaan harian"
     - Font Size: 13sp
     - Color: #558B2F

5. **Team Card**:
   - Width: Full - 32dp
   - Margin: 16dp
   - Background: #FFFFFF
   - Padding: 16dp
   - Corner Radius: 12dp
   - Elevation: 2dp
   
   - Title: "Tim Pengembang"
   - Font Size: 16sp
   - Font Weight: Bold
   - Color: #1B5E20
   - Margin Bottom: 12dp
   
   - Team members: "Dikembangkan oleh PAB Team"
   - Font Size: 13sp
   - Color: #558B2F

6. **Contact Card**:
   - Width: Full - 32dp
   - Margin: 16dp 16dp 32dp 16dp
   - Background: #E8F5E9
   - Padding: 16dp
   - Corner Radius: 12dp
   - Border: 1dp #2E7D32
   
   - Title: "Hubungi Kami"
   - Font Size: 16sp
   - Font Weight: Bold
   - Color: #1B5E20
   - Margin Bottom: 8dp
   
   - Email: "contact@ecolifehub.com"
   - Font Size: 12sp
   - Color: #2E7D32
   - Clickable/Link

---

### 12. SETTINGS SCREEN (375x812)
**Location**: Top-left at (4400, 0)

**Header Section** (Full Width, ~80dp):
- **Background**: Gradient #2E7D32 → #388E3C
- **Top Navigation**:
  - Back Arrow: 48x48 dp
  - Title: "Pengaturan"
  - Font Size: 20sp
  - Font Weight: Bold
  - Color: #FFFFFF

**Settings List** (Scrollable):
Starting at ~90dp

**Section 1: Account Settings**
- **Section Title**: "Akun"
  - Font Size: 12sp
  - Font Weight: Bold
  - Color: #2E7D32
  - Margin: 16dp 16dp 8dp 16dp
  - All caps

Each setting item:
- **Height**: 56dp
- **Width**: Full
- **Padding**: 16dp
- **Background**: #FFFFFF
- **Border Bottom**: 0.5dp #E0E0E0
- **Align**: Center vertically

**Items**:
1. **Edit Profile**
   - Icon: ic_profile (24x24)
   - Title: "Edit Profil"
   - Font Size: 14sp
   - Color: #1B5E20
   - Arrow: ic_arrow_right (24x24)

2. **Change Password**
   - Icon: lock icon (24x24)
   - Title: "Ubah Kata Sandi"
   - Font Size: 14sp
   - Color: #1B5E20
   - Arrow: ic_arrow_right (24x24)

**Section 2: App Settings**
- **Section Title**: "APLIKASI"
  - Font Size: 12sp
  - Font Weight: Bold
  - Color: #2E7D32
  - Margin: 24dp 16dp 8dp 16dp

**Items**:
1. **Dark Mode**
   - Icon: moon icon (24x24)
   - Title: "Mode Gelap"
   - Font Size: 14sp
   - Color: #1B5E20
   - Toggle switch: Right side
   - Toggle ON: #2E7D32
   - Toggle OFF: #A5D6A7

2. **Notifications**
   - Icon: bell icon (24x24)
   - Title: "Notifikasi"
   - Font Size: 14sp
   - Color: #1B5E20
   - Toggle switch: Right side

3. **Language**
   - Icon: language icon (24x24)
   - Title: "Bahasa"
   - Font Size: 14sp
   - Color: #1B5E20
   - Subtitle: "Bahasa Indonesia"
   - Font Size: 12sp
   - Color: #A5D6A7
   - Arrow: ic_arrow_right (24x24)

**Section 3: About**
- **Section Title**: "LAINNYA"
  - Font Size: 12sp
  - Font Weight: Bold
  - Color: #2E7D32
  - Margin: 24dp 16dp 8dp 16dp

**Items**:
1. **About**
   - Icon: ic_about (24x24)
   - Title: "Tentang Aplikasi"
   - Font Size: 14sp
   - Color: #1B5E20
   - Arrow: ic_arrow_right (24x24)

2. **Logout**
   - Icon: logout icon (24x24) - Red
   - Title: "Keluar"
   - Font Size: 14sp
   - Color: #F44336
   - Arrow: ic_arrow_right (24x24)

---

## Additional Design Notes

### Navigation
- **Back Button**: Always present on detail/secondary screens
- **Color**: #FFFFFF on gradient backgrounds
- **Size**: 48x48 dp with padding
- **Style**: Ripple effect on tap

### Status Bar
- **Color**: Match header gradient (Primary Dark)
- **Icons**: White
- **Text**: White

### Dividers
- **Color**: #E0E0E0
- **Height**: 0.5-1dp
- **Margin**: 0 (full width) or 16dp (card internal)

### Cards & Shadows
- **Elevation**: 2-4dp (MD shadow)
- **Shadow Color**: rgba(0, 0, 0, 0.1)
- **Corner Radius**: 12-16dp (usually 12dp for small, 16dp for medium)

### Icons Guidelines
- **All icons**: Should be green-themed (Primary colors)
- **Simple, flat design**: Avoid complex gradients
- **Consistent stroke weight**: 2dp
- **Outline style**: Preferred for consistency

### Buttons
- **Primary Button**: Green (#2E7D32) background, white text
- **Secondary Button**: White background, green border + text
- **Disabled**: Gray (#C8E6C9) background, disabled text color
- **Touch feedback**: Ripple effect at 20% opacity

### Animations
- **Screen transitions**: Fade or slide
- **Button ripple**: 200ms
- **Progress bar**: Smooth animation
- **List scroll**: Smooth and natural

---

## Export Specifications

### Mobile App
- **Device**: 375x812 px (iPhone design)
- **DPI**: 1x (will be scaled for different devices)
- **Safe Area**: Top 44px (status bar), Bottom 34px (navigation)

### Export Formats
- **UI Components**: PNG (transparent) @ 2x resolution
- **Icons**: SVG or PNG @ 1x/2x/3x
- **Colors**: Use exact hex codes provided

---

## Design System Components to Create

### Reusable Components
1. **Button Primary** (56x48 dp)
2. **Button Secondary** (56x48 dp)
3. **Input Field** (56x48 dp)
4. **Card Small** (120x140 dp)
5. **Card Medium** (300x180 dp)
6. **Card Large** (Full-32dp x variable)
7. **List Item** (Full x 56-80 dp)
8. **Badge** (Variable x 24 dp)
9. **Progress Bar** (Full x 8 dp)
10. **Icon Button** (48x48 dp)

---

## Final Checklist

- [ ] All 12 screens created
- [ ] Colors match exact hex codes
- [ ] Typography sizes and weights correct
- [ ] Spacing follows 8dp grid system
- [ ] Icons are consistent and properly aligned
- [ ] Gradient header present on all relevant screens
- [ ] Cards have proper elevation/shadow
- [ ] Corner radius applied correctly
- [ ] All interactive elements are clearly defined
- [ ] Status bar and navigation consideration

---

**Created**: 2026-01-11
**Version**: 1.0
**Ready for Figma Design**: ✓
