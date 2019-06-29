provider "aws" {}

resource "aws_dynamodb_table" "rating_table" {
  name = var.table_name
  hash_key = var.table_hash_key
  attribute {
    name = var.table_hash_key
    type = var.table_hash_key_type
  }

  read_capacity = 25
  write_capacity = 25
}