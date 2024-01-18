package com.example.demo.repository

import com.example.demo.entity.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository: JpaRepository<Member, Int> {

}