# MoneyTracker - Figma Design Specification

## Project Overview
MoneyTracker adalah aplikasi mobile untuk mencatat dan melacak transaksi keuangan (pemasukan dan pengeluaran).

---

## Screen 1: Home Screen

### Layout Structure
- **Top App Bar** (Height: 56dp)
  - Title: "Pencatat Keuangan"
  - Background Color: Primary Container (#EADDFF or theme color)
  - Text Color: On Primary Container

### Summary Card (Prominent)
- **Current Balance Display**
  - Font: Headline Large (32sp), Bold
  - Color: Green (#2E7D32) if positive, Red if negative
  - Label: "Saldo Saat Ini"

- **Two Summary Items (Row)**
  - **Pemasukan (Income)**
    - Label: "Pemasukan"
    - Amount: Green (#2E7D32)
    - Font: Title Medium, Bold
  
  - **Pengeluaran (Expense)**
    - Label: "Pengeluaran"
    - Amount: Red (#FF0000)
    - Font: Title Medium, Bold

### Transaction List Section
- **Section Header**
  - Text: "Transaksi Terakhir"
  - Font: Title Medium
  - Padding: 16.dp

- **Transaction Item Cards** (Repeating)
  - Layout: Row with title on left, amount on right
  - Background: Surface Variant
  - Padding: 16.dp
  - Fields:
    - **Title** (Left): Transaction name (Medium font)
    - **Amount** (Right, Bold): Currency formatted
    - **Date** (Below title): Formatted date (Body Small)
    - **Type Badge** (Optional): Income/Expense indicator with color

### Floating Action Button
- **Position**: Bottom-right corner
- **Icon**: Plus icon (+)
- **Color**: Primary color
- **Action**: Opens Add Transaction Dialog

---

## Screen 2: Add Transaction Dialog

### Dialog Layout
- **Title**: "Tambah Transaksi" or similar
- **Dismiss/Cancel**: Top-right X button

### Form Fields

1. **Transaction Title Input**
   - Placeholder: "Nama Transaksi"
   - Type: Text input
   - Required: Yes

2. **Amount Input**
   - Placeholder: "Jumlah"
   - Type: Numeric input
   - Keyboard: Number pad
   - Required: Yes

3. **Transaction Type Selection**
   - Option A: Radio buttons or Chip selection
     - "Pemasukan" (Income) - Green theme
     - "Pengeluaran" (Expense) - Red theme
   - OR
   - Option B: Dropdown/Menu selector

### Dialog Actions
- **Cancel Button**
  - Text: "Batal" or "Cancel"
  - Action: Dismiss dialog
  
- **Add Button**
  - Text: "Tambah" or "Add"
  - Action: Save transaction and close dialog
  - Enabled only when all required fields filled

---

## Design System

### Colors
| Element | Light | Dark |
|---------|-------|------|
| Primary | #6750A4 | #D0BCFF |
| On Primary | #FFFFFF | #21005E |
| Primary Container | #EADDFF | #4F378B |
| Secondary | #625B71 | #CAC4D0 |
| Tertiary | #7D5260 | #FFD8E4 |
| Error/Expense | #B3261E | #F2B8B5 |
| Success/Income | #2E7D32 | #B1E21E |
| Background | #FFFBFE | #1C1B1F |
| Surface | #FEF7FF | #302E37 |

### Typography
- **Display Large**: 57sp, 64 line-height
- **Headline Large**: 32sp, 40 line-height, Bold
- **Title Medium**: 16sp, 24 line-height, Bold
- **Body Medium**: 14sp, 20 line-height
- **Label Small**: 11sp, 16 line-height
- **Label Medium**: 12sp, 16 line-height

### Spacing
- Standard padding: 16.dp
- Card elevation: 4.dp
- Item spacing: 8.dp (vertical)

### Component Styles
- **Cards**: Elevated, radius 12.dp
- **Buttons**: Contained, height 40.dp, radius 20.dp
- **TextFields**: Outlined style
- **FAB**: 56x56.dp, radius 16.dp

---

## Screens to Design (Priority Order)

### Priority 1 (Core)
1. ✅ Home Screen
2. ✅ Add Transaction Dialog

### Priority 2 (Future)
3. Transaction Detail Screen
4. Edit Transaction Screen
5. Transaction History/Filter Screen
6. Settings Screen
7. Monthly/Yearly Reports Screen

---

## Assets to Create

### Icons Needed
- Add icon (+)
- Edit icon (pencil)
- Delete icon (trash)
- Income icon (arrow up/wallet+)
- Expense icon (arrow down/payment)
- Filter icon
- Settings icon

### Color Tokens
- Create color styles in Figma for all primary colors
- Create typography styles for consistent text appearance

---

## Responsive Considerations
- Designed for 360x800dp (Mobile Standard)
- Should scale for tablets (600x900+)
- Safe areas: 20.dp padding on sides

---

## Notes
- Uses Material Design 3 (Material You)
- Dark mode support included
- RTL-ready layout structure
- Accessibility: Minimum touch target 48x48.dp
