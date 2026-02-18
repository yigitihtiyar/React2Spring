import http from "@/lib/http";

export function Logout()
{
    return http.post("/api/v1/logout");
}