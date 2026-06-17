local key = KEYS[1]
local capacity = tonumber(ARGV[1])
local refillTokens = tonumber(ARGV[2])
local refillPeriodSeconds = tonumber(ARGV[3])
local requestedTokens = tonumber(ARGV[4])
local now = tonumber(ARGV[5])

local bucket = redis.call('HMGET', key, 'tokens', 'last_refill')
local tokens = tonumber(bucket[1])
local lastRefill = tonumber(bucket[2])

if tokens == nil then
    tokens = capacity
    lastRefill = now
else
    local elapsedTime = math.max(0, now - lastRefill)
    local refillPeriods = math.floor(elapsedTime / refillPeriodSeconds)
    if refillPeriods > 0 then
        tokens = math.min(capacity, tokens + (refillPeriods * refillTokens))
        lastRefill = lastRefill + (refillPeriods * refillPeriodSeconds)
    end
end

if tokens >= requestedTokens then
    tokens = tokens - requestedTokens
    redis.call('HMSET', key, 'tokens', tokens, 'last_refill', lastRefill)
    redis.call('EXPIRE', key, refillPeriodSeconds * 2)
    return 1
else
    redis.call('HMSET', key, 'tokens', tokens, 'last_refill', lastRefill)
    redis.call('EXPIRE', key, refillPeriodSeconds * 2)
    return 0
end
