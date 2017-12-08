#### Fix existing code starting from a client's complaint: 
### **It stopped working**

---

This module presents an application that will recover messages before starting and being considered "active".
* While recovering, (until activate request is received) no messages are processed, they are just queued for processing
* After activation, all queued messages will be processed, afterwards any arrived message is processed.

---


Starting from 2 jstack view over on application, you should try and find the issue along with improving any functional or design issues you consider.

