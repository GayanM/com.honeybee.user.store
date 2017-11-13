# com.honeybee.user.store
This is a extended JDBC user store manager written to support PBKDF2 password hashing. 
1. Edit user-mgt.xml under JDBCUserStoreManager element 
       "UserStoreManager class="com.honeybee.user.store.HoneybeeJDBCUserStoreManager"
        
2. Configurable properties in PBKDF2 password hashing for HoneybeeJDBCUserStoreManager   
       AlgorithmName / IterationCount / KeyLength
      
