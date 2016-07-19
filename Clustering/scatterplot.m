fileID = fopen('iris.data');
C = textscan(fileID,'%f %f %f %f %s','Delimiter',',');
fclose(fileID);
x = C{1}; y = C{3};
scatter(x,y)

z = cov(x,y);
covariance = z(1,2);

pcc = covariance/(std(x)*std(y))