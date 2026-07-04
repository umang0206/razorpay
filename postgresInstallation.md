# PostgreSQL 17 Setup on macOS (M1/M2/M3) using Homebrew

## Prerequisites

### 1. Verify Homebrew Installation

Check if Homebrew is installed on your system:

```bash
brew --version
```

If Homebrew is not installed, install it from:
https://brew.sh

---

## Install PostgreSQL 17

### 2. Install PostgreSQL 17 using Homebrew

```bash
brew install postgresql@17
```

### 3. Verify the Installation

```bash
psql --version
```

Expected output:

```text
psql (PostgreSQL) 17.x
```

---

## Start PostgreSQL Service

### 4. Start PostgreSQL

```bash
brew services start postgresql@17
```

### 5. Verify Service Status

```bash
brew services list
```

Expected output:

```text
Name          Status   User
postgresql@17 started  <username>
```

---

## Troubleshooting

### Problem: PostgreSQL Service Fails to Start

If you see:

```text
postgresql@17 error 1
```

there is a high chance that another PostgreSQL instance is already running on port `5432`.

### Step 1: Check Which Process Is Using Port 5432

```bash
sudo lsof -nP -iTCP:5432
```

Example output:

```text
COMMAND   PID      USER
postgres  540   postgres
```

### Step 2: Identify the Running PostgreSQL Instance

```bash
ps -p <PID> -o command=
```

Example:

```bash
ps -p 540 -o command=
```

Output:

```text
/Library/PostgreSQL/17/bin/postgres -D /Library/PostgreSQL/17/data
```

### Step 3: Stop Existing PostgreSQL Processes

```bash
sudo pkill postgres
```

### Step 4: Verify Port 5432 Is Free

```bash
sudo lsof -nP -iTCP:5432
```

If no output is displayed, the port is available.

### Step 5: Restart PostgreSQL 17

```bash
brew services restart postgresql@17
```

Verify again:

```bash
brew services list
```

---

## Configure PostgreSQL User

Connect to PostgreSQL:

```bash
psql postgres
```

List existing users:

```sql
\du
```

Create a new superuser (optional):

```sql
CREATE ROLE postgres WITH LOGIN SUPERUSER PASSWORD 'postgres';
```

Or set a password for the current user:

```sql
ALTER USER <username> PASSWORD 'your_password';
```

Exit PostgreSQL:

```sql
\q
```

---

# Install DBeaver Community Edition

### Install using Homebrew

```bash
brew install --cask dbeaver-community
```

### Launch DBeaver

```bash
open -a "DBeaver"
```

Alternatively, launch DBeaver from:

* Applications
* Spotlight Search (`Cmd + Space`)

---

# Connect PostgreSQL in DBeaver

1. Open **DBeaver**
2. Click **New Database Connection**
3. Select **PostgreSQL**
4. Enter the following details:

| Field    | Value                             |
| -------- | --------------------------------- |
| Host     | localhost                         |
| Port     | 5432                              |
| Database | postgres                          |
| Username | postgres (or your macOS username) |
| Password | your configured password          |

5. Click **Test Connection**
6. Click **Finish**

You should now be able to view and manage your PostgreSQL databases using DBeaver.

---

# Useful Commands

```bash
# Start PostgreSQL
brew services start postgresql@17

# Stop PostgreSQL
brew services stop postgresql@17

# Restart PostgreSQL
brew services restart postgresql@17

# Check service status
brew services list

# Check active PostgreSQL processes
ps aux | grep postgres

# Check which process is using port 5432
sudo lsof -nP -iTCP:5432

# Kill all PostgreSQL processes
sudo pkill postgres

# Connect to PostgreSQL
psql postgres
```
