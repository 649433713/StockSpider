db.properties中
更改连接数据库user password等

period代表更新时间，单位是s
createOrUpdate 若值为1，则创建表，并插入一次数据（首次启动使用）
		之后启动改为0，以period为间隔 update 数据

启动需要jre环境，双击start.bat即可