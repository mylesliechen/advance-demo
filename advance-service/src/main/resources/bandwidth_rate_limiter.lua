
redis.replicate_commands()

local now = redis.call("time")[1]

local tokens_key = KEYS[1]
local timestamp_key = tokens_key .. "[timestamp_key]"

local rate = tonumber(ARGV[1])
local capacity = tonumber(ARGV[2])
local requested = tonumber(ARGV[3])

local fill_time = capacity/rate

local last_tokens = tonumber(redis.call("get", tokens_key))
local last_refreshed = tonumber(redis.call("get", timestamp_key))

if last_tokens == nil or last_refreshed == nil then
    last_tokens = capacity
    last_refreshed = now
end

local delta = math.max(0, now-last_refreshed)
local filled_tokens = math.min(capacity, last_tokens+(delta*rate))
--local filled_tokens = last_tokens+(delta*rate)
local new_tokens = filled_tokens - requested
local sleep = 0
if new_tokens < 0 then
    sleep = (0 - new_tokens) / rate
end

local ttl = math.floor(fill_time*2 + sleep)

redis.call("setex", tokens_key, ttl, new_tokens)
redis.call("setex", timestamp_key, ttl, now)

redis.log(redis.LOG_WARNING, "tokens_key " .. tokens_key)
redis.log(redis.LOG_WARNING, "rate " .. rate)
redis.log(redis.LOG_WARNING, "capacity " .. capacity)
redis.log(redis.LOG_WARNING, "requested " .. requested)
redis.log(redis.LOG_WARNING, "fill_time " .. fill_time)
redis.log(redis.LOG_WARNING, "last_tokens " .. last_tokens)
redis.log(redis.LOG_WARNING, "last_refreshed " .. last_refreshed)
redis.log(redis.LOG_WARNING, "delta " .. delta)
redis.log(redis.LOG_WARNING, "filled_tokens " .. filled_tokens)
redis.log(redis.LOG_WARNING, "new_tokens " .. new_tokens)
redis.log(redis.LOG_WARNING, "sleep " .. sleep)

return sleep .. ""
