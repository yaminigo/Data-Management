finaltable = vertcat(unique(x'),histc(x',unique(x')))';

firstvalue = finaltable(1,1);

lastvalue = finaltable(size(finaltable,1),1);

valuespread = lastvalue-firstvalue+1;
mid = valuespread/2;

bucket1range = firstvalue + mid - 0.1;

values = finaltable(:,1);

bucket1 = values(values <= bucket1range);

bucket2 = values(values > bucket1range);

bucket1freq = sum(finaltable(1:size(bucket1,1),2));

bucket2freq = sum(finaltable(size(bucket1,1)+1:end,2));



bucket = {'#1';'#2'};

allOneString1 = sprintf('%.1f,' , bucket1);
allOneString1 = allOneString1(1:end-1);% strip final comma

allOneString2 = sprintf('%.1f,' , bucket2);
allOneString2 = allOneString2(1:end-1);% strip final comma
values = {allOneString1;allOneString2};

freqinbucket = {bucket1freq;bucket2freq};

totalfreq = sum(finaltable(:,2));
varbucket = {var(bucket1);var(bucket2)};

equiWidthTable = table(values,freqinbucket,varbucket,'RowNames',bucket)

a = [bucket1freq;bucket2freq];
b = [var(bucket1);var(bucket2)];
V4equiWidthTable = sum(a.*b)/totalfreq


partition = totalfreq/2;

include = 0;
for i=1:size(finaltable,1)
  if (sum(finaltable(1:i,2)) == partition)
      break;
  elseif(sum(finaltable(1:i,2)) > partition)
      include = 1;
      break;
  end
end

if (include==1)
    bucket1 = finaltable(1:i,1);
    bucket2 = finaltable(i:end,1);
else
    bucket1 = finaltable(1:i,1);
    bucket2 = finaltable(i+1:end,1);
end

allOneString1 = sprintf('%.1f,' , bucket1);
allOneString1 = allOneString1(1:end-1);% strip final comma

allOneString2 = sprintf('%.1f,' , bucket2);
allOneString2 = allOneString2(1:end-1);% strip final comma
values = {allOneString1;allOneString2};

freqinbucket = {partition;partition};
varbucket = {var(bucket1);var(bucket2)};
equidepthTable = table(values,freqinbucket,varbucket,'RowNames',bucket)

a = [partition;partition];
b = [var(bucket1);var(bucket2)];

V4equidepthTable = sum(a.*b)/totalfreq

diffTable = diff(finaltable(:,1));


[M,I] = max(single(diffTable));

bucket1 = finaltable(1:I,1);
bucket2 = finaltable(I+1:end,1);
allOneString1 = sprintf('%.1f,' , bucket1);
allOneString1 = allOneString1(1:end-1);% strip final comma

allOneString2 = sprintf('%.1f,' , bucket2);
allOneString2 = allOneString2(1:end-1);% strip final comma
values = {allOneString1;allOneString2};

bucket1freq = sum(finaltable(1:I,2));
bucket2freq = sum(finaltable(I+1:end,2));
freqinbucket = {bucket1freq;bucket2freq};
varbucket = {var(bucket1);var(bucket2)};
maxDiff = table(values,freqinbucket,varbucket,'RowNames',bucket)
a = [bucket1freq;bucket2freq];
b = [var(bucket1);var(bucket2)];
V4maxDiffTable = sum(a.*b)/totalfreq


