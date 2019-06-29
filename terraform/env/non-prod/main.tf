provider "aws" {
  region = var.region
  profile = var.profile
}

module "dynamodb_table" {
  source = "../../module/dynamodb"

  providers = {
    aws = aws
  }

  table_name = var.table_name
  table_hash_key = var.table_hash_key
  table_hash_key_type = var.table_hash_key_type
}