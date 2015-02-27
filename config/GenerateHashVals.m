clear;

boardSize = 19;
players = 4;
maxCaptures = 10;
boardHashVals = randi([-2^31, 2^31], boardSize*boardSize, players, 'int32');
captureHashVals = randi([-2^31, 2^31], maxCaptures/2, players, 'int32');

boardHashVals = int64(boardHashVals) + 2^31;
captureHashVals = int64(captureHashVals) + 2^31;

% Quick check
figure;
hist(double(reshape(boardHashVals, 1, boardSize^2*players)));
figure;
hist(double(reshape(captureHashVals, 1, players*maxCaptures/2)));

% Manually writing because dlmwrite doesnt like 64 bit ints.
f = fopen('BoardHashValues.csv', 'w');
for r = 1:length(boardHashVals(:,1))
    line = '';
    for c = 1:length(boardHashVals(1,:))
        line = [line, int2str(boardHashVals(r,c)), ','];
    end
    line = [line(1:end-1), '\n'];
    fprintf(f, line);
end
fclose(f);

f = fopen('CaptureHashValues.csv', 'w');
for r = 1:length(captureHashVals(:,1))
    line = '';
    for c = 1:length(captureHashVals(1,:))
        line = [line, int2str(captureHashVals(r,c)), ','];
    end
    line = [line(1:end-1), '\n'];
    fprintf(f, line);
end
fclose(f);