import subprocess
import sqlite3
import os

adb_path = r"C:\Users\Joestar\AppData\Local\Android\Sdk\platform-tools\adb.exe"

files = ['bersihin_database', 'bersihin_database-wal', 'bersihin_database-shm']

for fname in files:
    p = subprocess.run([adb_path, 'exec-out', 'run-as', 'com.pab.aplikasibersihin', 'cat', f'databases/{fname}'], capture_output=True)
    with open(fname, 'wb') as f:
        f.write(p.stdout)

try:
    conn = sqlite3.connect('bersihin_database')
    c = conn.cursor()
    c.execute("SELECT passwordHash FROM users WHERE email='dimasnurdiansyah912@gmail.com'")
    result = c.fetchone()
    if result:
        print("PASSWORD_FOUND:", result[0])
    else:
        print("PASSWORD_NOT_FOUND")
except Exception as e:
    print("ERROR:", e)
