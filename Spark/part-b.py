import sys
from pyspark import SparkContext

sc = SparkContext(appName="part-b")
lines = sc.textFile("purchasedatatextfiles/product.txt")
proddata = lines.map(lambda x: x.split('\t'))
lines = sc.textFile("purchasedatatextfiles/purchase.txt")
purchasedata = lines.map(lambda x: x.split('\t'))
lines = sc.textFile("purchasedatatextfiles/company.txt")
companydata = lines.map(lambda x: x.split('\t'))
lines = sc.textFile("purchasedatatextfiles/person.txt")

uscompanies = companydata.filter(lambda z: z[2]=="us").map(lambda x: (x[0],x[2]))
usproducts = proddata.map(lambda x: (x[3],x[0])).join(uscompanies).map(lambda x: x[1])
joindata = usproducts.join(purchasedata.map(lambda x: (x[3],x[0])))
data = joindata.map(lambda x: x[1]).reduce(lambda a,b: list(a)+list(b))
USresult = sc.parallelize(data).distinct().filter(lambda z: z != "us").collect()

#People who bought Chinese products
uscompanies = companydata.filter(lambda z: z[2]=="china").map(lambda x: (x[0],x[2]))
usproducts = proddata.map(lambda x: (x[3],x[0])).join(uscompanies).map(lambda x: x[1])
joindata = usproducts.join(purchasedata.map(lambda x: (x[3],x[0])))
data = joindata.map(lambda x: x[1]).reduce(lambda a,b: list(a)+list(b))
Chinaresult = sc.parallelize(data).distinct().filter(lambda z: z != "china").collect()
print list(set(USresult)-set(Chinaresult))