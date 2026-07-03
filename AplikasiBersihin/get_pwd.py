import subprocess
import sqlite3
import os

adb_path = r"C:\Users\Joestar\AppData\Local\Android\Sdk\platform-tools\adb.exe"
p = subprocess.run([adb_path, 'exec-out', 'run-as', 'com.pab.aplikasibersihin', 'cat', 'databases/bersihin_database'], capture_output=True)

with open('db_temp.sqlite', 'wb') as f:
    f.write(p.stdout)

try:
    conn = sqlite3.connect('db_temp.sqlite')
    c = conn.cursor()
    c.execute("SELECT password FROM users WHERE email='dimasnurdiansyah912@gmail.com'")
    result = c.fetchone()
    if result:
        print("PASSWORD_FOUND:", result[0])
    else:
        print("PASSWORD_NOT_FOUND")
except Exception as e:
    print("ERROR:", e)
