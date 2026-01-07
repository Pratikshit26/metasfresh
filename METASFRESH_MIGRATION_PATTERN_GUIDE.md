# Metasfresh Standard Migration Pattern - Study Guide

## 📚 Overview

Metasfresh uses the **ADempiere Data Dictionary Pattern** for managing database schema and UI changes. This is a metadata-driven architecture where the application reads its structure from the database at runtime.

---

## 🏗️ Core Concepts

### The ADempiere/Metasfresh Architecture

Metasfresh is built on **ADempiere** (a fork of Compiere), which uses a **Data Dictionary** approach:

- **Metadata-Driven**: UI, tables, columns, and fields are defined in database tables
- **Runtime Generation**: The application reads metadata and generates UI dynamically
- **Version Controlled**: All changes are tracked via SQL migration scripts
- **Multi-Tenant**: Supports multiple organizations with client/org hierarchy

---

## 🗂️ Migration File Structure

```
backend/
└── de.metas.adempiere.adempiere/
    └── migration/
        └── src/main/sql/postgresql/
            ├── ddl/                    # Data Definition Language
            │   └── ops/functions/      # Database functions
            └── system/                 # System metadata changes
                ├── 10-de.metas.adempiere/    # Core framework
                ├── 11-de.metas.jms/          # JMS module
                ├── 12-de.metas.event/        # Event module
                └── 70-de.metas.fresh/        # Fresh (ERP) module
```

### File Naming Convention

```
[SEQUENCE]_[TYPE]_[ISSUE]_[DESCRIPTION].sql

Examples:
5782850_sys_gh26910_Dunning_Candidate_Window_Update.sql
5760750_sys_gh26910_Create_C_Period_for_2026_to_2030.sql

Where:
- SEQUENCE: Numeric order (ensures execution sequence)
- TYPE: sys (system), cli (client-specific)
- ISSUE: GitHub issue number (gh26910)
- DESCRIPTION: Human-readable description
```

---

## 🎯 Standard Migration Pattern (6 Steps)

### Step 1: Define Database Column (`AD_Column`)

Every database column must be registered in the metadata:

```sql
-- Column: C_Dunning_Candidate.C_Dunning_ID
INSERT INTO AD_Column (
    AD_Client_ID,           -- Client ID (0 = System)
    AD_Column_ID,           -- Unique column ID
    AD_Element_ID,          -- Links to AD_Element (shared definition)
    AD_Org_ID,              -- Organization ID
    AD_Reference_ID,        -- Data type (19=ID, 30=Search, 10=String, etc.)
    AD_Table_ID,            -- Parent table
    ColumnName,             -- Physical column name
    ColumnSQL,              -- Optional: Virtual column SQL
    Description,            -- Column description
    Help,                   -- Help text
    EntityType,             -- Module identifier
    FieldLength,            -- Max length
    IsActive,               -- Active flag
    IsMandatory,            -- Required field
    Name,                   -- Display name
    Created, CreatedBy, Updated, UpdatedBy
) VALUES (...);
```

**Key Fields:**
- `AD_Reference_ID`: Defines data type
  - 10 = String
  - 11 = Integer
  - 12 = Amount
  - 13 = ID
  - 19 = Table
  - 30 = Search (dropdown with search)

### Step 2: Create Translation Records (`AD_Column_Trl`)

Support multiple languages:

```sql
INSERT INTO AD_Column_Trl (
    AD_Language, AD_Column_ID, Name, 
    IsTranslated, AD_Client_ID, AD_Org_ID, ...
) 
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 
       'N', t.AD_Client_ID, t.AD_Org_ID, ...
FROM AD_Language l, AD_Column t 
WHERE l.IsActive='Y'
  AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') 
  AND t.AD_Column_ID=591821;
```

### Step 3: Update Column Translation from Element

Synchronize with AD_Element (central definition):

```sql
SELECT update_Column_Translation_From_AD_Element(838);
```

This copies translations from `AD_Element` to `AD_Column_Trl`.

### Step 4: Define Field in Tab (`AD_Field`)

Create a field (UI representation of a column) for a specific tab:

```sql
-- Field: Window -> Tab -> Field Name
INSERT INTO AD_Field (
    AD_Client_ID,
    AD_Column_ID,           -- Links to AD_Column
    AD_Field_ID,            -- Unique field ID
    AD_Org_ID,
    AD_Tab_ID,              -- Parent tab
    DisplayLength,          -- UI display length
    Description,
    Help,
    IsDisplayed,            -- Show in form
    IsDisplayedGrid,        -- Show in grid
    IsReadOnly,             -- Read-only field
    IsSameLine,             -- Display on same line as previous
    Name,                   -- Field label
    SeqNo,                  -- Sequence in form
    SeqNoGrid,              -- Sequence in grid
    Created, CreatedBy, Updated, UpdatedBy
) VALUES (...);
```

### Step 5: Position UI Element (`AD_UI_Element`)

Define where the field appears in the modern webUI:

```sql
-- UI Element: Window -> Tab -> Section -> Group -> Element
INSERT INTO AD_UI_Element (
    AD_Client_ID,
    AD_Field_ID,            -- Links to AD_Field
    AD_Org_ID,
    AD_Tab_ID,
    AD_UI_ElementGroup_ID,  -- UI group (e.g., "main", "advanced")
    AD_UI_Element_ID,
    AD_UI_ElementType,      -- 'F' = Field, 'A' = Action
    Description,
    Help,
    IsDisplayed,            -- Visible
    IsDisplayedGrid,        -- Show in grid view
    Name,
    SeqNo,                  -- Position in form
    SeqNoGrid,              -- Position in grid
    Created, CreatedBy, Updated, UpdatedBy
) VALUES (...);
```

### Step 6: Update Sequences

Adjust sequences when inserting new elements:

```sql
-- Move existing fields down to make room
UPDATE AD_UI_Element 
SET IsDisplayedGrid='Y', SeqNoGrid=80
WHERE AD_UI_Element_ID=548994;

UPDATE AD_UI_Element 
SET IsDisplayedGrid='Y', SeqNoGrid=90
WHERE AD_UI_Element_ID=548989;
```
---

## 📊 Data Dictionary Tables

### Core Tables Hierarchy

```
AD_Element (Master Definition)
    ↓
AD_Column (Table Column)
    ↓
AD_Field (UI Field in Tab)
    ↓
AD_UI_Element (WebUI Position)
```

### Important Metadata Tables

| Table | Purpose |
|-------|---------|
| `AD_Element` | Master definition (name, description shared across columns) |
| `AD_Column` | Database column definition |
| `AD_Table` | Database table definition |
| `AD_Field` | UI field (links column to tab) |
| `AD_Tab` | Tab definition (belongs to window) |
| `AD_Window` | Window definition |
| `AD_UI_Element` | WebUI element positioning |
| `AD_UI_ElementGroup` | WebUI element grouping |
| `AD_Reference` | Data type definitions |
| `AD_Ref_List` | List values (for dropdown lists) |

---

## 🔍 Real Example Breakdown

### From Dunning Candidate Window Update

```sql
-- 1. CREATE COLUMN
INSERT INTO AD_Column (
    AD_Column_ID=591821,
    AD_Table_ID=540396,           -- C_Dunning_Candidate table
    ColumnName='C_Dunning_ID',
    AD_Reference_ID=19,           -- Type: Table ID
    ColumnSQL='(SELECT C_Dunning_ID from C_DunningLevel 
                WHERE C_DunningLevel_ID = C_Dunning_Candidate.C_DunningLevel_ID)',
    Name='Mahnung',               -- German: Dunning
    Description='Dunning Rules for overdue invoices',
    EntityType='de.metas.dunning',
    IsActive='Y',
    IsMandatory='N'
) VALUES (...);

-- 2. CREATE TRANSLATIONS
INSERT INTO AD_Column_Trl (...);

-- 3. UPDATE FROM ELEMENT
SELECT update_Column_Translation_From_AD_Element(838);

-- 4. MAKE IT SEARCHABLE
UPDATE AD_Column 
SET IsSelectionColumn='Y', SelectionColumnSeqNo=30
WHERE AD_Column_ID=591821;

-- 5. CREATE FIELD IN TAB
INSERT INTO AD_Field (
    AD_Field_ID=760967,
    AD_Column_ID=591821,
    AD_Tab_ID=540424,             -- Dunning Candidates tab
    Name='Mahnung',
    SeqNo=0,                      -- Form sequence
    SeqNoGrid=220                 -- Grid sequence
) VALUES (...);

-- 6. CREATE UI ELEMENT
INSERT INTO AD_UI_Element (
    AD_UI_Element_ID=641306,
    AD_Field_ID=760967,
    AD_UI_ElementGroup_ID=541192, -- "default" group
    SeqNo=47,                     -- Form position
    IsDisplayedGrid='Y',
    SeqNoGrid=70                  -- Grid position (reordered)
) VALUES (...);

-- 7. REORDER OTHER ELEMENTS
UPDATE AD_UI_Element 
SET SeqNoGrid=80 
WHERE AD_UI_Element_ID=548994;   -- Dunning Level moved to 80
```

---

## 🎨 UI Layout Structure

### WebUI Hierarchy

```
AD_Window (e.g., "Dunning Disposition")
  └── AD_Tab (e.g., "Dunning Candidates")
      └── AD_UI_Section (e.g., "main", "advanced")
          └── AD_UI_ElementGroup (e.g., "default", "flags", "dates")
              └── AD_UI_Element (e.g., "Dunning Rules", "Open Amount")
```

### Layout Configuration

```sql
-- UI Section: main (primary section)
-- UI Group: default, flags, dates, org, stats,default state values over the meetng 
-- UI Element: Individual fields

main section
├── default group
│   ├── Business Partner
│   ├── Dunning Rules      [NEW - Position 47]
│   └── Dunning Level      [OLD - Position 48]
├── dunning group
│   ├── Open Amount
│   ├── Dunning Interest
│   └── Total Amount
├── dates group
│   ├── Dunning Date
│   └── Dunning Grace
└── flags group
    ├── Processed
    └── Dunning Doc Created
```

---

## 🔧 Helper Functions

### Common Functions Used in Migrations

```sql
-- Update translations from element
SELECT update_Column_Translation_From_AD_Element(838);

-- Update field translations
SELECT update_FieldTranslation_From_AD_Name_Element(838);

-- Create missing element links
SELECT AD_Element_Link_Create_Missing_Field(760967);

-- Update sequences
SELECT update_Sequences();
```

---

## 📝 Best Practices

### 1. **Always Use Unique IDs**
- Use ID ranges assigned to your module
- System IDs: < 1000000
- Custom IDs: > 1000000

### 2. **EntityType Management**
```sql
EntityType='de.metas.dunning'  -- Module identifier
EntityType='D'                  -- Dictionary (core)
```

### 3. **Translation Support**
- Always create translation records
- Use `update_*_Translation_From_AD_Element()` functions

### 4. **Sequence Numbers**
- Leave gaps (10, 20, 30...) for future insertions
- Grid sequences separate from form sequences

### 5. **Idempotency**
- Scripts should be safe to run multiple times
- Use `IF NOT EXISTS` when possible

```sql
-- Idempotent insert
INSERT INTO AD_Column (...)
SELECT ... 
WHERE NOT EXISTS (
    SELECT 1 FROM AD_Column 
    WHERE AD_Column_ID=591821
);
```

---

## 🚀 Execution Flow

### When metasfresh starts:

1. **Migration Runner** checks `AD_MigrationScript` table
2. Executes pending scripts in **sequence order**
3. Records execution in `AD_MigrationScript`
4. **Application** reads metadata from dictionary tables
5. **WebUI** generates interface dynamically

---

## 📖 Study Resources

### Key Directories to Explore

```bash
# Migration scripts
backend/de.metas.adempiere.adempiere/migration/

# Application data model
backend/de.metas.adempiere.adempiere/base/src/main/java/org/compiere/model/

# WebUI backend
backend/de.metas.ui.web.base/

# Fresh module (ERP-specific)
backend/de.metas.fresh/
```

### Understanding by Example

1. **Find a recent migration**: 
   ```bash
   ls -lt backend/*/migration/src/main/sql/postgresql/system/*/*.sql | head
   ```

2. **Study the pattern**:
   - Look for `INSERT INTO AD_Column`
   - Follow with `INSERT INTO AD_Field`
   - Then `INSERT INTO AD_UI_Element`

3. **Check the window**:
   ```sql
   SELECT * FROM AD_Window WHERE Name LIKE '%Dunning%';
   SELECT * FROM AD_Tab WHERE AD_Window_ID = ?;
   SELECT * FROM AD_Field WHERE AD_Tab_ID = ?;
   ```

---

## 🎯 Quick Reference

### Common AD_Reference_ID Values

| ID | Type | Description |
|----|------|-------------|
| 10 | String | Text field |
| 11 | Integer | Whole number |
| 12 | Amount | Currency amount |
| 13 | ID | Database ID |
| 14 | Text | Long text |
| 15 | Date | Date only |
| 16 | DateTime | Date + Time |
| 17 | List | Static dropdown |
| 18 | Table | Foreign key |
| 19 | TableDir | Direct table lookup |
| 20 | Yes-No | Boolean |
| 22 | Number | Decimal number |
| 28 | Button | Button field |
| 30 | Search | Searchable dropdown |

---

## 💡 Key Takeaways

1. **Metadata-Driven**: Everything is data in database tables
2. **Layered**: Column → Field → UI Element
3. **Versioned**: SQL scripts track all changes
4. **Multilingual**: Built-in translation support
5. **Sequential**: Execution order matters
6. **Reusable**: AD_Element provides shared definitions

---

## 🔗 Next Steps for Study

1. **Read existing migrations** in your module of interest
2. **Trace a field** from AD_Column to UI
3. **Practice writing** a simple column addition
4. **Study AD_Element** to understand reusability
5. **Explore WebUI** layout with AD_UI_* tables

---

## 📚 Additional Reading

- **ADempiere Documentation**: http://wiki.adempiere.net/
- **Metasfresh GitHub**: https://github.com/metasfresh/metasfresh
- **Data Dictionary Concepts**: Study `org.compiere.model.MColumn` class
- **Migration Tool**: `de.metas.migration.cli` module

---

**Generated:** January 6, 2026  
**Purpose:** Study guide for understanding metasfresh's standard migration pattern
