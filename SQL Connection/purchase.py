'''
Created on Mar 9, 2016

@author: yaminigoyal
'''
import sys
import mysql.connector
from mysql.connector import errorcode


try:
    cnx = mysql.connector.connect(user='inf551', password='inf551',
                              host='localhost',
                              database='inf551')
except mysql.connector.Error as err:
    if err.errno == errorcode.ER_ACCESS_DENIED_ERROR:
        print("Something is wrong with your user name or password")
    elif err.errno == errorcode.ER_BAD_DB_ERROR:
        print("Database does not exist")
    else:
        print(err)
else:
    cursor = cnx.cursor()
    buyer = sys.argv[1]
    query = ("SELECT distinct(product) FROM Purchase WHERE buyer =") + "\"" + buyer + "\""
    cursor.execute(query,buyer)
    i = 0
    for (product) in cursor:
        i = 1
        print(product)
    if i == 0:
        print "No data found"
    cursor.close()
    cnx.close()