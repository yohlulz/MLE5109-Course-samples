```bash
$ cat /dev/urandom | base64 | head -c 1M > /tmp/1M
$ cat /dev/urandom | base64 | head -c 10M > /tmp/10M
$ cat /dev/urandom | base64 | head -c 100M > /tmp/100M
$ cat /dev/urandom | base64 | head -c 1G > /tmp/1G
```

$ ls -lha /tmp/
```bash
-rw-r--r--  1 ovidiu ovidiu 100M Nov  3 19:55 100M
-rw-r--r--  1 ovidiu ovidiu  10M Nov  3 19:55 10M
-rw-r--r--  1 ovidiu ovidiu 1,0G Nov  3 19:56 1G
-rw-r--r--  1 ovidiu ovidiu 1,0M Nov  3 19:55 1M
```
