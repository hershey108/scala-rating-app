variable "table_name" {
  description = "DynamoDB Table Name"
}

variable "table_hash_key" {
  description = "Name of hash-key attribute"
}

variable "table_hash_key_type" {
  description = "Type of hash-key attribute. One of (S)tring, (N)umber or (B)inary"
}